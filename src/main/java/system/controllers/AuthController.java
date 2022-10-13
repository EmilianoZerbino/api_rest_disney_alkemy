package system.controllers;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.dto.authdto.JWTAuthResponseDTO;
import system.dto.authdto.LoginDTO;
import system.dto.authdto.RegisterDTO;
import system.entities.Role;
import system.entities.User;
import system.exceptions.AppException;
import system.repositories.interfaces.IRoleRepository;
import system.repositories.interfaces.IUserRepository;
import system.security.JwtTokenProvider;
import system.utilities.SendEmailBySendGrid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public SendEmailBySendGrid sendEmailBySendGrid;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//obtener el token del jwtTokenProvider
		String token = jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTAuthResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) throws IOException{
		if(userRepository.existsByUsername(registerDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		
		if(userRepository.existsByEmail(registerDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		
		Role roles = roleRepository.findByName("ROLE_USER").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		sendEmailBySendGrid.sendEmailBySendGrid(user.getUsername(),user.getEmail());
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestBody LoginDTO loginDTO){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		User user = userRepository.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail()).orElseThrow(()->new AppException(HttpStatus.NOT_FOUND,"Usuario no encontrado con ese username o email"));
		user.setRoles(null);
		userRepository.save(user);
		userRepository.delete(user);
		return new ResponseEntity<String>("Usuario eliminado Correctamente",HttpStatus.OK);
		
	}
	/*
	public void sendEmailBySendGrid(String newUserEmail) throws IOException {
		
		Email from = new Email("somosfundacionmas2@gmail.com");
		Email to = new Email(newUserEmail);
		String subject = "Bienvenido al mundo de Disney";
		Content content = new Content("text/plain","Estamos orgullosos de que sea parte de nosotros.");
		Mail mail = new Mail(from,subject,to,content);
		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
			
		}catch (IOException ex){
			throw new IOException(ex.getMessage());
		}
	}*/
}

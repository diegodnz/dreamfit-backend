package com.dreamfitbackend.configs.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dreamfitbackend.configs.exceptions.MessageException;
import com.dreamfitbackend.domain.usuario.User;
import com.dreamfitbackend.domain.usuario.UserRepository;
import com.dreamfitbackend.domain.usuario.enums.Role;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazon.s3.endpoint}")
    private String endpointUrl;
    @Value("${amazon.s3.default-bucket}")
    private String bucketName;
    @Value("${amazon.aws.access-key-id}")
    private String accessKey;
    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;
    
    @Autowired
    private UserRepository userRepo;
    
    @PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = new AmazonS3Client(credentials);
    }
    
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    
    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
    
    public void saveNewUrl(User user, String url) {
    	user.setProfilePicture(url);
    	userRepo.save(user);
    }
    
    public User checkUser(User loggedUser, String uuid) {
    	User updateUser = userRepo.findByUuid(uuid);
    	if (updateUser == null) {
    		throw new MessageException("Usuário inválido", HttpStatus.BAD_REQUEST);
    	}
    	if (loggedUser.getRole_user() != Role.ADMIN && !loggedUser.getUuid().matches(uuid)) {
    		throw new MessageException("Sem permissão", HttpStatus.FORBIDDEN);
    	}
    	return updateUser;
    }
    
    public String uploadFile(MultipartFile multipartFile, String fileName) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);            
            fileUrl = endpointUrl + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
        	throw new MessageException("Houve um erro no upload da imagem, tente novamente", HttpStatus.BAD_REQUEST);
        }
        return fileUrl;
    }

    public void deleteFileFromS3Bucket(String fileUrl) {
    	try {
	        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
    	} catch (Exception e) {}        
    }
}
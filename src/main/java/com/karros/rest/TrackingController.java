/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of the
 * entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.rest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.karros.dto.FileUploadDTO;
import com.karros.dto.LatestTrackDTO;
import com.karros.service.TrackingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chaul
 *
 */
@RestController
public class TrackingController {
    private final Logger logger = LoggerFactory.getLogger(TrackingController.class);
    
	@Autowired
	private TrackingService trackingService;
	
	@PostMapping("/{userId}/upload")
	public ResponseEntity<String> uploadFile(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile uploadfile) {
	    logger.debug("Upload file!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<String>("Please select a file!", HttpStatus.BAD_REQUEST);
        }
        
        try {
            trackingService.saveFileUpload(userId, uploadfile.getOriginalFilename(), uploadfile.getInputStream());
        } catch (Exception e) {
            logger.warn("Error while reading file data!", e);
            return new ResponseEntity<String>(String.format("Error while reading file data: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(String.format("File %s is successfully uploaded!", uploadfile.getOriginalFilename()), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/latest")
	public List<LatestTrackDTO> getLatestTrack(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size) {
	    logger.debug("Get latest track!");
	    return trackingService.getLatestTrack(page, size);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<?> getFileDetail(@RequestParam(name = "id") Optional<Long> fileId) {
	    logger.debug("Get file detail!");
	    if (!fileId.isPresent()) {
	        return ResponseEntity.badRequest().body("Query parameter 'id' is required!");
	    }
	    
	    FileUploadDTO result = trackingService.getFileDetail(fileId.get());
	    if (result == null) {
	        return new ResponseEntity<String>(String.format("fileId=%s not found.", fileId.get()), HttpStatus.NOT_FOUND);
	    }
	    
	    return new ResponseEntity<FileUploadDTO>(result, HttpStatus.OK);
	}
}
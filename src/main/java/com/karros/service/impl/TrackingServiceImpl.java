/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.karros.dao.FileUploadRepository;
import com.karros.dto.FileUploadDTO;
import com.karros.dto.LatestTrackDTO;
import com.karros.dto.converter.DTOConverter;
import com.karros.entity.FileUpload;
import com.karros.rest.TrackingController;
import com.karros.service.TrackingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chaul
 *
 */
@Service
@Transactional
public class TrackingServiceImpl implements TrackingService {
    private final Logger logger = LoggerFactory.getLogger(TrackingServiceImpl.class);
    
    @Autowired
    private FileUploadRepository fileUploadRepo;
    
    @Override
    public void saveFileUpload(Long userId, String fileName, InputStream gpxFile) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            FileUploadDTO gpxFileData = xmlMapper.readValue(gpxFile, FileUploadDTO.class);
            FileUpload entity = DTOConverter.convertObject(userId, fileName, gpxFileData);
            fileUploadRepo.save(entity);
        } catch (IOException e) {
            logger.warn("Exception while saving fileupload Entity: ", e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    public List<LatestTrackDTO> getLatestTrack(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<FileUpload> items = fileUploadRepo.findAll(pageable);
        List<LatestTrackDTO> dtos = new ArrayList<>();
        if (items.hasContent()) {
            dtos.addAll(items.stream().map(DTOConverter::convertToLatestTrackDTO).collect(Collectors.toList()));
        }
        return dtos;
    }
    
    @Override
    public FileUploadDTO getFileDetail(Long fileId) {
        Optional<FileUpload> fileUpload = fileUploadRepo.findById(fileId);
        if (!fileUpload.isPresent()) {
            return null;
        }
        
        return DTOConverter.convertObject(fileUpload.get());
    }
}

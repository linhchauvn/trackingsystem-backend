/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.karros.dto.FileUploadDTO;
import com.karros.dto.LatestTrackDTO;

/**
 * @author chaul
 *
 */
public interface TrackingService {
    void saveFileUpload(Long userId, String fileName, InputStream gpxFile) throws IOException;
    List<LatestTrackDTO> getLatestTrack(Integer page, Integer size);
    FileUploadDTO getFileDetail(Long fileId);
}

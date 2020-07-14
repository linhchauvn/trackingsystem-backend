/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.dao;
import com.karros.entity.FileUpload;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chaul
 *
 */
@Repository
public interface FileUploadRepository extends PagingAndSortingRepository<FileUpload, Long>{

}

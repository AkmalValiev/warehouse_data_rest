package uz.pdp.task1.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.task1.entity.Attachment;
import uz.pdp.task1.entity.AttachmentContent;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.repository.AttachmentContentRepository;
import uz.pdp.task1.repository.AttachmentRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public List<Attachment> getAttachments(){
        return attachmentRepository.findAll();
    }

    public void getAttachment(Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent= attachmentContentRepository.getByAttachment_Id(id);
            if (optionalAttachmentContent.isPresent()){
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition", "attachment; filename=\""+attachment.getName()+"\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
    }

    public ApiResponse uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null){
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();
            Attachment attachment = new Attachment();
            attachment.setContentType(contentType);
            attachment.setSize(size);
            attachment.setName(originalFilename);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(savedAttachment);
            attachmentContent.setBytes(file.getBytes());
            attachmentContentRepository.save(attachmentContent);
            return new ApiResponse("File saqlandi!", true);
        }
        return new ApiResponse("File saqlanmadi!", false);
    }

    public ApiResponse deleteFile(Integer id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Optional<AttachmentContent> optional = attachmentContentRepository.getByAttachment_Id(id);
            if (optional.isPresent()){
                attachmentContentRepository.delete(optional.get());
                attachmentRepository.delete(optionalAttachment.get());
                return new ApiResponse("File o'chirildi!", true);
            }
        }
        return new ApiResponse("File topilmadi!", false);
    }


}

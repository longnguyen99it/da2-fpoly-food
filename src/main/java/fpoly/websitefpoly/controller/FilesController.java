package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.repository.ProductRepository;
import fpoly.websitefpoly.response.ResponseMessage;
import fpoly.websitefpoly.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@CrossOrigin("http://localhost:8080")
public class FilesController {

    @Autowired
    private FilesStorageService storageService;

    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {

            message = "Uploaded the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(storageService.save(file)));
        } catch (Exception e) {
            System.out.println(e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

//  @GetMapping("/files")
//  public ResponseEntity<List<FileInfo>> getListFiles() {
//    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//      String filename = path.getFileName().toString();
//      String url = MvcUriComponentsBuilder
//          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//
//      return new FileInfo(filename, url,null);
//    }).collect(Collectors.toList());
//
//    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//  }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = (Resource) storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
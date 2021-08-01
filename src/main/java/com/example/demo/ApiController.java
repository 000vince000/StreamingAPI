package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.Charset;


@RequestMapping("/api/stream")
@RestController
public class ApiController {
    @GetMapping(value = "/csv")
    public ResponseEntity<StreamingResponseBody> getCsvFile() {
        StreamingResponseBody stream = output -> {
            BufferedWriter bw = null;
            try {
                Writer w = new OutputStreamWriter(System.out);
                bw = new BufferedWriter(w);
                bw.write("output test");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
//            Writer writer = new BufferedWriter(new OutputStreamWriter(output));
//            writer.write("name,rollNo"+"\n");
//            for (int i = 1; i <= 10000; i++) {
////                Student st = new Student("Name" + i, i);
////                writer.write(st.getName() + "," + st.getRollNo() + "\n");
////                writer.flush();
//            }
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=small/rcv_banks.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(stream);
    }


}

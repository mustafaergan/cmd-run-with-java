package com.bat.bat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping(path ="/admin")
public class AdminController {

    @GetMapping(value = "/add/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String addAdmin(@PathVariable("name") String name)  throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "net localgroup administrators "+name+" /add");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
            sb.append(line);
        }
        return sb.toString();
    }

    @GetMapping(value = "/remove/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String removeAdmin(@PathVariable("name") String name)  throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "net localgroup administrators "+name+" /delete");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
            sb.append(line);
        }
        return sb.toString();
    }


}

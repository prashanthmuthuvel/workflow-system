package com.workflow.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

@Service
public class RestClientUpload {
	
	@Value("${python.url}")
	private String m_pythonUrl;
	
	public String exampleMultiPartUpload(File fileToUpload, String signature) {
        final String API_URI = "http://localhost:5000/handle_form";

        final ClientConfig config = new DefaultClientConfig();
        final Client client = Client.create(config);
        final WebResource resource = client.resource(API_URI);

        // the file to upload, represented as FileDataBodyPart
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",
                fileToUpload,
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
        fileDataBodyPart.setContentDisposition(
                FormDataContentDisposition.name("file")
                        .fileName(fileToUpload.getName()).build());

        // some json to send to the server as an element of the multi part request
        final JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("character", "Jabba the Hutt");
        jsonToSend.put("movie", "Return of the Jedi");
        jsonToSend.put("isGoodGuy", false);

        /* create the MultiPartRequest with:
         * Text field called "description"
         * JSON field called "characterProfile"
         * Text field called "filename"
         * Binary body part called "file" using fileDataBodyPart
         */
        final MultiPart multiPart = new FormDataMultiPart()
                .field("textToBeFound", signature, MediaType.TEXT_PLAIN_TYPE)
                .field("filename", fileToUpload.getName(), MediaType.TEXT_PLAIN_TYPE)
                .bodyPart(fileDataBodyPart);
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        // POST request final
        ClientResponse response = resource
                .type("multipart/form-data").post(ClientResponse.class,
                        multiPart);
        String result = getStringFromInputStream(response.getEntityInputStream());
        System.out.println("INFO >>> Response from API was: " + result);
        client.destroy();
        return result;
    }

    // convert InputStream to String
    private String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        final StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}

package com.appsecurity.Controller;

import com.appsecurity.Entity.Anexo;
import com.appsecurity.Entity.User;
import com.appsecurity.Services.AnexoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AnexoCtrl {

    @Autowired
    private AnexoService service;

    List<Anexo> anexoList;

    public ModelAndView getModelAnView(String URLTemplate) throws LoginException {
        if (User.getCurrent() == null)
            throw new LoginException("Usuário não logado");
        else
            return new ModelAndView(URLTemplate);
    }

    @RequestMapping("/anexos")
    public ModelAndView anexos() {
        ModelAndView mv = null;
        try {
            mv = getModelAnView("Anexos");
            anexoList = service.getAllAnexosFromUser(User.getCurrent().getCodigo());
            mv.getModelMap().addAttribute("user", User.getCurrent());
            mv.getModelMap().addAttribute("anexos", anexoList);
        } catch (LoginException ex) {
            return new LoginCtrl().login();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mv;
    }

    @RequestMapping(value = "download/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadArquivo(@PathVariable(value = "id") String id) throws Exception {
        Optional<Anexo> file = anexoList.stream()
                .filter(anexo -> anexo.getCodigo() == Integer.valueOf(id))
                .findFirst();
        if (file.get() == null)
            throw new Exception(String.format("Arquivo de ID: %s não encontrado", id));
        Anexo anexo = file.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",
                anexo.getDescricao().concat(".").concat(anexo.getTipo()));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(anexo.getAnexo().length);
        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(anexo.getAnexo())), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView add() throws Exception {
        Anexo anexo = new Anexo();
        ModelAndView mv = new ModelAndView("AnexoAdd");
        mv.getModelMap().addAttribute("anexo", anexo);
        return mv;
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public ModelAndView editArquivo(@PathVariable(value = "id") String id) throws Exception {
        Optional<Anexo> file = anexoList.stream()
                .filter(anexo -> anexo.getCodigo() == Integer.valueOf(id))
                .findFirst();
        if (file.get() == null)
            throw new Exception(String.format("Arquivo de ID: %s não encontrado", id));
        Anexo anexo = file.get();
        ModelAndView mv = new ModelAndView("AnexoAdd");
        mv.getModelMap().addAttribute("anexo", anexo);
        return mv;
    }

    @RequestMapping(value = "editsave/{id}", method = RequestMethod.POST)
    public ModelAndView saveEdit(@PathVariable("id") String id,
                                 @RequestParam("anexo") MultipartFile myFile,
                                 @RequestParam("nameFile") String nameFile) throws Exception {
        Anexo anexo = null;
        if (!id.isEmpty() && !id.equals("0")) {
            Optional<Anexo> file = anexoList.stream()
                    .filter(anx -> anx.getCodigo() == Integer.valueOf(id))
                    .findFirst();
            if (file.get() == null)
                throw new Exception(String.format("Arquivo de ID: %s não encontrado", id));
            anexo = file.get();
        } else {
            anexo = new Anexo();
        }
        anexo.setDescricao(nameFile);
        anexo.setUser(User.getCurrent().getCodigo());
        if (!myFile.isEmpty()) {
            anexo.setAnexo(myFile.getBytes());
            String extension = FilenameUtils.getExtension(myFile.getOriginalFilename());
            anexo.setTipo(extension);
        }
        service.editAnexo(anexo);
        return new ModelAndView("redirect:/anexos");
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeArquivo(@PathVariable(value = "id") String id) throws Exception {
        Optional<Anexo> file = anexoList.stream()
                .filter(anexo -> anexo.getCodigo() == Integer.valueOf(id))
                .findFirst();
        if (file.get() == null)
            throw new Exception(String.format("Arquivo de ID: %s não encontrado", id));
        Anexo anexo = file.get();
        service.removeAnexoByID(anexo);
        return new ModelAndView("redirect:/anexos");
    }
}

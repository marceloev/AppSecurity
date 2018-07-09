package com.appsecurity.Controller;

import com.appsecurity.Entity.User;
import com.appsecurity.Services.LoginService;
import com.appsecurity.Utils.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;

@RestController
@RequestMapping("/")
public class LoginCtrl {

    @Autowired
    private LoginService service;

    @RequestMapping("/")
    public ModelAndView redirectLogin() {
        return login();
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("Login");
        return mv;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        User.setCurrent(null);
        return login();
    }

    @PostMapping("/")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        ModelAndView mv = null;
        try {
            User user = service.getUserByLogin(username);
            /*Verificar login SEM CRIPTOGRAFIA*/
            /*if (user.getSenha().equals(password)) {
                User.setCurrent(user);
                return new ModelAndView("redirect:/anexos");
            } else
            throw new NoResultException("NoResult");*/
            /*Verificar login COM CRIPTOGRAFIA*/
            if(user.getSenha().equals(new Password(false, password).getPassword())) {
                User.setCurrent(user);
                return new ModelAndView("redirect:/anexos");
            } else {
                throw new NoResultException("NoResult");
            }
        } catch (NoResultException ex) {
            mv = new ModelAndView("Login");
            mv.getModelMap().addAttribute("throw001", true);
        } catch (Exception ex) {
            mv = new ModelAndView("Login");
            mv.getModelMap().addAttribute("throw000",
                    String.format("Erro ao tentar efetuar login\n%s", ex.getMessage()));
            ex.printStackTrace();
        }
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar() {
        ModelAndView mv = new ModelAndView("Cadastrar");
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar(@RequestParam("name") String name,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        ModelAndView mv = null;
        try {
            /*Sem criptografia - SALVAR NOVO USUÁRIO*/
            /*User user = new User(name, username.toUpperCase(), password);*/
            /*Com criptografia - SALVAR NOVO USUÁRIO*/
            User user = new User(name,
                    username.toUpperCase(),
                    new Password(false, password).getPassword());
            service.addUser(user);
            mv = new ModelAndView("Login");
            mv.getModelMap().addAttribute("throw003",
                    "Cadastro efetuado com sucesso!");
        } catch (Exception ex) {
            mv = new ModelAndView("Cadastrar");
            mv.getModelMap().addAttribute("throw000",
                    "Erro ao tentar cadastrar usuário\n" + ex.getMessage());
        }
        return mv;
    }
}

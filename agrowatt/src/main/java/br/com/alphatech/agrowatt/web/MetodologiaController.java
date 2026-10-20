package br.com.alphatech.agrowatt.web;

import br.com.alphatech.agrowatt.servico.CatalogoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MetodologiaController {

    private final CatalogoService catalogo;

    public MetodologiaController(CatalogoService catalogo) {
        this.catalogo = catalogo;
    }

    @GetMapping("/como-calculamos")
    public String comoCalculamos(Model model) {
        model.addAttribute("residuos", catalogo.residuos());
        model.addAttribute("premissas", catalogo.listarPremissas());
        return "como-calculamos";
    }
}

package br.com.alphatech.agrowatt.web;

import br.com.alphatech.agrowatt.servico.CalculoService;
import br.com.alphatech.agrowatt.servico.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalculoController {

    private final CalculoService calculos;
    private final CatalogoService catalogo;

    public CalculoController(CalculoService calculos, CatalogoService catalogo) {
        this.calculos = calculos;
        this.catalogo = catalogo;
    }

    @GetMapping("/calculos/novo")
    public String novo(Model model) {
        model.addAttribute("form", new CalculoForm());
        model.addAttribute("residuos", catalogo.residuos());
        return "calculos/novo";
    }

    @PostMapping("/calculos")
    public String calcular(@Valid @ModelAttribute("form") CalculoForm form, BindingResult erros, Model model) {
        if (!erros.hasFieldErrors("idResiduo") && !catalogo.existeResiduo(form.getIdResiduo())) {
            erros.rejectValue("idResiduo", "inexistente", "Escolha um resíduo da lista");
        }
        if (erros.hasErrors()) {
            model.addAttribute("residuos", catalogo.residuos());
            return "calculos/novo";
        }
        Integer id = calculos.calcular(form.getIdResiduo(), form.massaEmKg());
        return "redirect:/calculos/" + id;
    }

    @GetMapping("/calculos")
    public String historico(Model model) {
        model.addAttribute("linhas", calculos.listarHistorico());
        return "calculos/historico";
    }

    @GetMapping("/calculos/{id}")
    public String resultado(@PathVariable Integer id, Model model) {
        model.addAttribute("d", calculos.buscar(id));
        return "calculos/resultado";
    }
}

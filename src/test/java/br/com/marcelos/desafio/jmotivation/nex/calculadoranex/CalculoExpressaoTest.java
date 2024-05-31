package br.com.marcelos.desafio.jmotivation.nex.calculadoranex;

import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.dto.ExpressaoDTO;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Expressao;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Usuario;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services.CalculoExpressaoService;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services.ExpressaoService;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculoExpressaoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpressaoService expressaoService;

    @MockBean
    private CalculoExpressaoService calculoExpressaoService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("testuser");
        usuario.setPassword("password");

        mockMvc.perform(post("/api/usuarios/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        token = "Bearer " + generateToken();
    }

    private String generateToken() {
        when(jwtUtil.generateToken(anyString())).thenReturn("mocked-token");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.extractUsername(anyString())).thenReturn("testuser");
        return "mocked-token";
    }

    @Test
    @Order(1)
    public void testSomaDeDoisNumerosInteiros_Cenario1() throws Exception {
        ExpressaoDTO expressaoDTO = new ExpressaoDTO();
        expressaoDTO.setExpressao("2+2");

        Expressao expressao = new Expressao();
        expressao.setId(1L);
        expressao.setExpressao("2+2");
        expressao.setResultado("4");

        when(expressaoService.findByExpressao(anyString())).thenReturn(Optional.empty());
        when(calculoExpressaoService.resultado(anyString())).thenReturn("4");
        when(expressaoService.save(any(Expressao.class))).thenReturn(expressao);

        mockMvc.perform(post("/api/expressoes/calculonovo")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expressaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expressao").value("2+2"))
                .andExpect(jsonPath("$.resultado").value("4"));
    }
    
    @Test
    @Order(2)
    public void testRetornarCalculoGravado_Cenario2() throws Exception {
        ExpressaoDTO expressaoDTO = new ExpressaoDTO();
        expressaoDTO.setExpressao("2.2+2.2");

        Expressao expressao = new Expressao();
        expressao.setId(1L);
        expressao.setExpressao("2.2+2.2");
        expressao.setResultado("4.4");

        when(expressaoService.findByExpressao("2.2+2.2")).thenReturn(Optional.empty(), Optional.of(expressao));
        when(calculoExpressaoService.resultado("2.2+2.2")).thenReturn("4.4");
        when(expressaoService.save(any(Expressao.class))).thenReturn(expressao);

        mockMvc.perform(post("/api/expressoes/calculonovo")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expressaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expressao").value("2.2+2.2"))
                .andExpect(jsonPath("$.resultado").value("4.4"));

        mockMvc.perform(post("/api/expressoes/calculonovo")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expressaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expressao").value("2.2+2.2"))
                .andExpect(jsonPath("$.resultado").value("4.4"));
    }

    @Test
    @Order(3)
    public void testOperadorMultiplicacaoComSoma_Cenario3() throws Exception {
        // Preparar a expressão e o resultado esperado
        ExpressaoDTO expressaoDTO = new ExpressaoDTO();
        expressaoDTO.setExpressao("2.3*2.3+5");

        Expressao expressao = new Expressao();
        expressao.setId(1L);
        expressao.setExpressao("2.3*2.3+5");
        expressao.setResultado("10.29");

        when(expressaoService.findByExpressao(anyString())).thenReturn(Optional.empty());
        when(calculoExpressaoService.resultado("2.3*2.3+5")).thenReturn("10.29");
        when(expressaoService.save(any(Expressao.class))).thenReturn(expressao);

        mockMvc.perform(post("/api/expressoes/calculonovo")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expressaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expressao").value("2.3*2.3+5"))
                .andExpect(jsonPath("$.resultado").value("10.29"));
    }
    
    @Test
    @Order(4)
    public void testArredondamentoParaCima_Cenario4() throws Exception {
        ExpressaoDTO expressaoDTO = new ExpressaoDTO();
        expressaoDTO.setExpressao("2.33/3");

        Expressao expressao = new Expressao();
        expressao.setId(1L);
        expressao.setExpressao("2.33/3");
        expressao.setResultado("0.78");

        when(expressaoService.findByExpressao(anyString())).thenReturn(Optional.empty());
        when(calculoExpressaoService.resultado("2.33/3")).thenReturn("0.78");
        when(expressaoService.save(any(Expressao.class))).thenReturn(expressao);

        mockMvc.perform(post("/api/expressoes/calculonovo")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expressaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expressao").value("2.33/3"))
                .andExpect(jsonPath("$.resultado").value("0.78"));
    }
    
 
    @Test
    @Order(5)
    public void testDivisaoPorZero_Cenario5() throws Exception {
        ExpressaoDTO expressaoDTO = new ExpressaoDTO();
        expressaoDTO.setExpressao("1/0");

        when(expressaoService.findByExpressao(anyString())).thenReturn(Optional.empty());
        when(calculoExpressaoService.resultado("1/0")).thenThrow(new ArithmeticException("Erro de Divisão por ZERO"));

        mockMvc.perform(post("/api/expressoes/calculonovo")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expressaoDTO)))
                .andExpect(status().isBadRequest());
    }
}

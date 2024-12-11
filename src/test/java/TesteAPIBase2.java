import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TesteAPIBase2 {
    @Before
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        Relatorio.getInstance(); // Inicia API
    }


    @Test
    public void CadastrarNovoPedidoDePet() {

        Relatorio.startTest("Cadastrar um novo pedido de Pet");

        String Body = "{\"id\": 0, \"petId\": 1, \"quantity\": 2, \"shipDate\": \"2024-12-11T12:00:00.000+0000\", \"status\": \"placed\", \"complete\": true}";

        given()
                .body(Body)
                .header("Content-Type", "application/json")

         .when()
                .post("/store/order")
         .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("petId", equalTo(1))
                .body("quantity", equalTo(2))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
        Relatorio.logPass("Requisição POST bem-sucedida");
    }


    @Test
    public void PesquisarPetInexistente() {
        Relatorio.startTest("Pesquisar Pet Inexistente");
        given()
                .pathParam("petId", "1")
        .when()
                .get("/{petId}")
        .then()
                .statusCode(404);
        Relatorio.logPass("Pet Inexistente");
    }

    @Test
    public void AtualizarDadosPetExistente() {

        Relatorio.startTest("Atualizar Dados Pet Existente");

        String Body = "{\"id\": 1, \"name\": \"Teste\", \"status\": \"Alterado\"}";
        given()
                .body(Body)
                .header("Content-Type", "application/json")
       .when()
                .put("/pet")
       .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Teste"))
                .body("status", equalTo("Alterado"));

       Relatorio.logPass("Requisição PUT bem-sucedida");
    }

    @Test
    public void PesquisarPetComStatusPending() {
        Relatorio.startTest("Pesquisar Pet Com Status Pending");
        given()
                .param("status", "pending")
        .when()
                .get("/pet/findByStatus")
        .then()
                .statusCode(200)
                .body("status", everyItem(equalTo("pending")));
        Relatorio.logPass("Pet Pendente");
    }


    @After
    public void tearDown() {
        Relatorio.endTest(); // Finaliza o relatório
    }
}

package ca.gbc.orderservice.stub;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {
    public  static void  stubInventoryCall(String skuCode, Integer quantity){
        stubFor(get(urlEqualTo("/api/inventory?skuCode="+skuCode+"&quantity="+quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("content-type","application/json")
                        .withBody("true"))
        );
    }
}

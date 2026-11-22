package com.myprojects.productcatalogservice.services;

import com.myprojects.productcatalogservice.dtos.FakeStoreProductDto;
import com.myprojects.productcatalogservice.exceptions.ThirdPartyApiException;
import com.myprojects.productcatalogservice.models.Category;
import com.myprojects.productcatalogservice.models.Product;
import com.myprojects.productcatalogservice.models.State;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService{

//    @Autowired
//    private RestTemplateBuilder restTemplateBuilder;
    private final RestTemplate restTemplate;

    // Constructor Injection
    public ProductService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }
    @Override
    public List<Product> getAllProducts() {
        String url = "https://fakestoreapi.com/products";
        ResponseEntity <List<FakeStoreProductDto >> fakeStoreProductsResponse;
        try{
            fakeStoreProductsResponse =
                    this.restTemplate.exchange(url,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<FakeStoreProductDto>>() {});
        }catch (HttpStatusCodeException ex){
            throw new ThirdPartyApiException(ex.getMessage(), ex.getStatusCode());
        }
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreProductsResponse.getBody();
        if(fakeStoreProductDtos == null)
            return null;
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtos){
            Product product = from(fakeStoreProductDto);
            products.add(product);
        }
        return products;
    }
    @Override
    public Product getProductById(Long productId) {
        String url = "https://fakestoreapi.com/products/{id}";
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity;
        try{
            fakeStoreProductDtoResponseEntity =
                    this.restTemplate.getForEntity(url,
                            FakeStoreProductDto.class,
                            productId);
        }catch (HttpStatusCodeException ex){
            throw new ThirdPartyApiException(ex.getMessage(), ex.getStatusCode());
        }
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if(fakeStoreProductDto == null)
            return null;
        return from(fakeStoreProductDto);
    }

    public Product from(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setState(State.ACTIVE);
        return product;
    }
}

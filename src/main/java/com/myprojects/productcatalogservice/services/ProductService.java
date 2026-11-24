package com.myprojects.productcatalogservice.services;

import com.myprojects.productcatalogservice.dtos.FakeStoreProductDto;
import com.myprojects.productcatalogservice.exceptions.ThirdPartyApiException;
import com.myprojects.productcatalogservice.models.Category;
import com.myprojects.productcatalogservice.models.Product;
import com.myprojects.productcatalogservice.models.State;
import jakarta.annotation.Nullable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
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
            Product product = from(fakeStoreProductDto, null, null);
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
        return from(fakeStoreProductDto, null, null);
    }

    @Override
    public Product editProduct(Long productId, Product product) {
        String url = "https://fakestoreapi.com/products/{id}";
        Long categoryId = product.getCategory().getId();
        String categoryDesc = product.getCategory().getDescription();
        FakeStoreProductDto fakeStoreProductDto = from(product);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity;
        try{
            fakeStoreProductDtoResponseEntity = requestForEntity(HttpMethod.PUT,
                                                                 url,
                                                                 fakeStoreProductDto,
                                                                 FakeStoreProductDto.class,
                                                                 productId);
        }catch (HttpStatusCodeException ex){
            throw new ThirdPartyApiException(ex.getMessage(), ex.getStatusCode());
        }
        FakeStoreProductDto editedFakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if(editedFakeStoreProductDto == null)
            return null;
        return from(editedFakeStoreProductDto, categoryId, categoryDesc);
    }

    public <T>ResponseEntity<T> requestForEntity
            (HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables){
        RequestCallback requestCallback = this.restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = this.restTemplate.responseEntityExtractor(responseType);
        return this.restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public Product from(FakeStoreProductDto fakeStoreProductDto,
                        @Nullable Long categoryId,
                        @Nullable String categoryDescription){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        category.setId(categoryId);
        category.setDescription(categoryDescription);
        product.setCategory(category);
        product.setState(State.ACTIVE);
        return product;
    }

    public FakeStoreProductDto from(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getName());
        return fakeStoreProductDto;
    }
}

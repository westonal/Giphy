package io.github.westonal.giphyapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.fabric8.mockwebserver.DefaultMockServer;
import io.github.westonal.giphyapi.dto.Pagination;
import io.github.westonal.giphyapi.dto.PaginationResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.github.westonal.giphyapi.ResourceLoader.resource;
import static org.junit.Assert.assertEquals;

public final class GiphyServiceTest {

    private DefaultMockServer server;
    private Retrofit retrofit;

    @Before
    public void setUp() {
        server = new DefaultMockServer();
        server.start();

        final OkHttpClient client = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/v1/gifs/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @After
    public void teardown() {
        server.shutdown();
    }

    @Test
    public void canGetTrending() {
        server.expect().withPath("/v1/gifs/trending?api_key=key&limit=25&offset=75")
                .andReturn(200, resource("paginationResponse.json"))
                .once();
        final PaginationResponse paginationResponse = getTrending(25, 75);
        final Pagination pagination = paginationResponse.getPagination();
        assertEquals(63621, pagination.getTotalCount());
        assertEquals(25, pagination.getCount());
        assertEquals(75, pagination.getOffset());
        assertEquals(3, paginationResponse.getGifs().size());
        assertEquals("NsCSaZBhPkjrHeztJi",
                paginationResponse.getGifs().get(0).getId());
        assertEquals("https://media1.giphy.com/media/NsCSaZBhPkjrHeztJi/200w.gif",
                paginationResponse.getGifs().get(0).getImages().getFixedWidth().getUrl());
    }

    @Test
    public void canGetSearch() {
        server.expect().withPath("/v1/gifs/search?api_key=key&q=cat&limit=25&offset=75")
                .andReturn(200, resource("paginationResponse.json"))
                .once();
        final PaginationResponse paginationResponse = getSearch("cat", 25, 75);
        final Pagination pagination = paginationResponse.getPagination();
        assertEquals(63621, pagination.getTotalCount());
        assertEquals(25, pagination.getCount());
        assertEquals(75, pagination.getOffset());
        assertEquals(3, paginationResponse.getGifs().size());
        assertEquals("3osxYdXvsGw6wT5lIY",
                paginationResponse.getGifs().get(1).getId());
        assertEquals("https://media3.giphy.com/media/3osxYdXvsGw6wT5lIY/200w.gif",
                paginationResponse.getGifs().get(1).getImages().getFixedWidth().getUrl());
    }

    @Test
    public void canGetOriginal() {
        server.expect().withPath("/v1/gifs/trending?api_key=key&limit=50&offset=100")
                .andReturn(200, resource("paginationResponse.json"))
                .once();
        final PaginationResponse paginationResponse = getTrending(50, 100);
        assertEquals("https://media2.giphy.com/media/26DOMeaD2gdGE44LK/giphy.gif",
                paginationResponse.getGifs().get(2).getImages().getOriginal().getUrl());
    }

    private PaginationResponse getTrending(final int limit, final int offset) {
        final GiphyApi giphyApi = retrofit.create(GiphyApi.class);
        try {
            return new GiphyService(giphyApi, "key")
                    .getTrending(limit, offset)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PaginationResponse getSearch(final String searchQuery, final int limit, final int offset) {
        final GiphyApi giphyApi = retrofit.create(GiphyApi.class);
        try {
            return new GiphyService(giphyApi, "key")
                    .getSearch(searchQuery, limit, offset)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

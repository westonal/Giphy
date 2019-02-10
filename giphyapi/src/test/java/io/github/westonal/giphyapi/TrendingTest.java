package io.github.westonal.giphyapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.fabric8.mockwebserver.DefaultMockServer;
import io.github.westonal.giphyapi.dto.Pagination;
import io.github.westonal.giphyapi.dto.TrendingResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.github.westonal.giphyapi.ResourceLoader.resource;
import static org.junit.Assert.assertEquals;

public final class TrendingTest {

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
                .andReturn(200, resource("trendingResponse.json"))
                .once();
        final TrendingResponse trendingResponse = getTrending(25, 75);
        final Pagination pagination = trendingResponse.getPagination();
        assertEquals(63621, pagination.getTotalCount());
        assertEquals(25, pagination.getCount());
        assertEquals(75, pagination.getOffset());
        assertEquals(3, trendingResponse.getGifs().size());
        assertEquals("https://media1.giphy.com/media/NsCSaZBhPkjrHeztJi/200w.gif",
                trendingResponse.getGifs().get(0).getImages().getFixedWidth().getUrl());
    }

    private TrendingResponse getTrending(final int limit, final int offset) {
        final GiphyApi giphyApi = retrofit.create(GiphyApi.class);
        final List<TrendingResponse> key = new GiphyService(giphyApi, "key")
                .getTrending(limit, offset)
                .test()
                .assertComplete()
                .values();
        assertEquals(1, key.size());
        return key.get(0);
    }
}

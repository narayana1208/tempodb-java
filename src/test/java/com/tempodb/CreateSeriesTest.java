package com.tempodb;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;


public class CreateSeriesTest {

  private static final String json = "{\"id\":\"id1\",\"key\":\"key1\",\"name\":\"name1\",\"tags\":[],\"attributes\":{}}";
  private static final String body = "{\"key\":\"key1\",\"name\":\"name1\",\"tags\":[],\"attributes\":{}}";
  private static final Series series = new Series("key1", "name1", new HashSet<String>(), new HashMap<String, String>());
  private static final Series series1 = new Series("key1", "name1", new HashSet<String>(), new HashMap<String, String>());

  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  @Test
  public void smokeTest() throws IOException {
    HttpResponse response = Util.getResponse(200, json);
    Client client = Util.getClient(response);

    Result<Series> result = client.createSeries(series);

    assertEquals(series1, result.getValue());
  }

  @Test
  public void testMethod() throws IOException {
    HttpResponse response = Util.getResponse(200, json);
    HttpClient mockClient = Util.getMockHttpClient(response);
    Client client = Util.getClient(mockClient);

    Result<Series> result = client.createSeries(series);

    HttpRequest request = Util.captureRequest(mockClient);
    assertEquals("POST", request.getRequestLine().getMethod());
  }

  @Test
  public void testUri() throws IOException, URISyntaxException {
    HttpResponse response = Util.getResponse(200, json);
    HttpClient mockClient = Util.getMockHttpClient(response);
    Client client = Util.getClient(mockClient);

    Result<Series> result = client.createSeries(series);

    HttpRequest request = Util.captureRequest(mockClient);
    URI uri = new URI(request.getRequestLine().getUri());
    assertEquals("/v1/series/", uri.getPath());
  }

  @Test
  public void testBody() throws IOException {
    HttpResponse response = Util.getResponse(200, json);
    HttpClient mockClient = Util.getMockHttpClient(response);
    Client client = Util.getClient(mockClient);

    Result<Series> result = client.createSeries(series);

    ArgumentCaptor<HttpPost> argument = ArgumentCaptor.forClass(HttpPost.class);
    verify(mockClient).execute(any(HttpHost.class), argument.capture(), any(HttpContext.class));
    assertEquals(body, EntityUtils.toString(argument.getValue().getEntity(), DEFAULT_CHARSET));
  }
}

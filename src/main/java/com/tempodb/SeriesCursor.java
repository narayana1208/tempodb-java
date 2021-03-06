package com.tempodb;

import java.net.URI;
import java.util.Iterator;

import org.apache.http.HttpRequest;

import static com.tempodb.util.Preconditions.*;


class SeriesCursor implements Cursor<Series> {
  private final URI uri;
  private final Client client;

  public SeriesCursor(URI uri, Client client) {
    this.uri = checkNotNull(uri);
    this.client = checkNotNull(client);
  }

  public Iterator<Series> iterator() {
    HttpRequest request = client.buildRequest(uri.toString());
    Result<SeriesSegment> result = client.execute(request, SeriesSegment.class);

    Iterator<Series> iterator = null;
    if(result.getState() == State.SUCCESS) {
      @SuppressWarnings("unchecked") // This cast is always ok
      SegmentIterator<Segment<Series>> segments = new SegmentIterator(client, result.getValue(), SeriesSegment.class);
      iterator = new SegmentInnerIterator<Series>(segments);
    } else {
      throw new TempoDBException(result.getMessage(), result.getCode());
    }
    return iterator;
  }
}

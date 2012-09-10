package com.tempodb.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *  Respresents metadata associated with the series. Each series has a
 *  globally unique id that is generated by the system and a user defined key.
 *  The key must be unique among all of your series. Each series may have a set
 *  of tags and attributes that can be used to filter series during bulk reads.
 *  Attributes are key/value pairs. Both the key and attribute must be strings.
 *  Tags are keys with no values. Tags must also be strings.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Series {
    private final String id;
    private final String key;
    private final String name;
    private final List<String> tags;
    private final Map<String, String> attributes;

    /**
     *  @param id unique series id (String)
     *  @param key user defined key (String)
     *  @param name human readable name for the series (String)
     *  @param attributes key/value pairs providing metadata for the series (Map - keys and values are Strings)
     *  @param tags (List of Strings)
     */
    @JsonCreator
    public Series(@JsonProperty("id") String id, @JsonProperty("key") String key,
            @JsonProperty("name") String name, @JsonProperty("tags") List<String> tags,
            @JsonProperty("attributes") Map<String, String> attributes) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.tags = tags;
        this.attributes = attributes;
    }

    /**
     *  @param id unique series id (String)
     *  @param key user defined key (String)
     */
    public Series(String id, String key) {
        this(id, key, "", new ArrayList<String>(), new HashMap<String, String>());
    }

    public String getId() { return id; }
    public String getKey() { return key; }
    public String getName() { return name; }
    public List<String> getTags() { return tags; }
    public Map<String, String> getAttributes() { return attributes; }

    @Override
    public String toString() {
        return String.format("Series: \n\tid:\t%s\n\tkey:\t%s\n\tname:\t%s\n\ttags:\t%s\n\tattr:\t%s", id, key, name, tags, attributes);
    }
}

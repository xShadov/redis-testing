package com.shadov.test.redis.name;

import io.vavr.collection.HashMap;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

@RedisHash("name")
public class NameEntry {
	@Id
	private String id;
	@Indexed
	private String group;
	private Map<String, String> catalog;

	public NameEntry(String id, String group, Map<String, String> catalog) {
		this.id = id;
		this.group = group;
		this.catalog = catalog;
	}

	public static NameEntry noId(String name, Map<String, String> catalog) {
		return new NameEntry(null, name, catalog);
	}

	public String getGroup() {
		return group;
	}

	public io.vavr.collection.Map<String, String> getCatalog() {
		return HashMap.ofAll(catalog);
	}

	public NameEntry addToCatalog(String name, String value) {
		return new NameEntry(this.id, this.group, HashMap.ofAll(this.catalog).put(name, value).toJavaMap());
	}

	@Override
	public String toString() {
		return "NameEntry{" +
				"id='" + id + '\'' +
				", group='" + group + '\'' +
				", catalog=" + catalog +
				'}';
	}
}

package com.shadov.test.redis.name;

import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<NameEntry, String> {
	Option<NameEntry> findByGroup(String group);
}

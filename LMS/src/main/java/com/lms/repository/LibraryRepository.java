package com.lms.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.entity.Library;

@Repository
@Transactional
public interface LibraryRepository extends JpaRepository<Library, Integer> {
	public Optional<Library> findByBookId(int bookId);

	public List<Library> findAllByBookId(int bookId);

	@Query(value = "select count(*) from library where book_id = :bookId", nativeQuery = true)
	public BigInteger countByBookId(int bookId);

	@Query(value = "select count(*) from library where user_name = :userName", nativeQuery = true)
	public BigInteger countByUserName(String userName);

	@Query(value = "select count(*) from library where book_id = :bookId and user_name = :userName", nativeQuery = true)
	public BigInteger countByBookIdAndUserName(int bookId, String userName);

	public void deleteAllByUserName(String userName);
}

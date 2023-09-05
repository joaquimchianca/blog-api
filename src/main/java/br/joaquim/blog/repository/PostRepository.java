package br.joaquim.blog.repository;

import br.joaquim.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<Post, Long> {
}

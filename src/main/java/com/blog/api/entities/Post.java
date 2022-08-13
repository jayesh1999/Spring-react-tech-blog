package com.blog.api.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name= "post_title",length = 100, nullable = false)
	private String title;
	
	@Column(length=1000)
	private String content;
	
	private String ImageName;
	
	private LocalDateTime  addedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(Integer postId, String title, String content, String imageName, LocalDateTime  addedDate, Category category,
			User user) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		ImageName = imageName;
		this.addedDate = addedDate;
		this.category = category;
		this.user = user;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return ImageName;
	}

	public void setImageName(String imageName) {
		ImageName = imageName;
	}

	public LocalDateTime  getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDateTime  addedDate) {
		this.addedDate = addedDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", title=" + title + ", content=" + content + ", ImageName=" + ImageName
				+ ", addedDate=" + addedDate + ", category=" + category + ", user=" + user + "]";
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	
	
}

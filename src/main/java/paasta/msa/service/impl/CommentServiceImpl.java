package paasta.msa.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import paasta.msa.dao.CommentDAO;
import paasta.msa.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource(name = "commentDAO")
	private CommentDAO commentDAO;

	public Map<String, Object> getCommentCount(Map<String, Object> paramMap) throws Exception {
		return commentDAO.getCommentCount(paramMap);
	}

	public List<Object> getCommentList(Map<String, Object> paramMap) throws Exception {
		return commentDAO.getCommentList(paramMap);
	}

	public int postComment(Map<String, Object> paramMap) throws Exception {
		return commentDAO.postComment(paramMap);
	}

	public int putComment(Map<String, Object> paramMap) throws Exception {
		return commentDAO.putComment(paramMap);
	}

	public int deleteComment(Map<String, Object> paramMap) throws Exception {
		return commentDAO.deleteComment(paramMap);
	}

}

package paasta.msa.service;

import java.util.List;
import java.util.Map;

public interface CommentService {

	public Map<String, Object> getCommentCount(Map<String, Object> paramMap) throws Exception;
	
	public List<Object> getCommentList(Map<String, Object> paramMap) throws Exception;
	
	public int postComment(Map<String, Object> paramMap) throws Exception;

	public int putComment(Map<String, Object> paramMap) throws Exception;

	public int deleteComment(Map<String, Object> paramMap) throws Exception;

}

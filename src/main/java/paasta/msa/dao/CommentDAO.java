package paasta.msa.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import paasta.msa.common.CommonDAO;

@Repository("commentDAO")
public class CommentDAO extends CommonDAO {
	
	public Map<String, Object> getCommentCount(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectOne("paasta.msa.service.impl.CommentMapper.getCommentCount", paramMap);
	}

	public List<Object> getCommentList(Map<String, Object> paramMap) throws Exception {
		return getSqlSession().selectList("paasta.msa.service.impl.CommentMapper.getCommentList", paramMap);
	}

	public int postComment(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().insert("paasta.msa.service.impl.CommentMapper.postComment", paramMap);
	}

	public int putComment(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().update("paasta.msa.service.impl.CommentMapper.putComment", paramMap);
	}

	public int deleteComment(Map<String, Object> paramMap) throws Exception {

		return getSqlSession().delete("paasta.msa.service.impl.CommentMapper.deleteComment", paramMap);
	}

}

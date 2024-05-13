package com.duaz.microservices.admin.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.duaz.microservices.admin.model.Movie;
import com.duaz.microservices.admin.model.ReturnMessege;
import com.duaz.microservices.admin.utility.DatabaseUtility;


@Transactional
@Repository
public class MovieServices {

	/**
	 * 
	 */
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired()
    @Qualifier("dbNonHibernate.datasource")
    private DataSource ds;
	
	/**
	 * getListMovie
	 * 
	 * @return List<Movie>
	 * @throws Exception
	 */
	public List<Movie> getListMovie() throws Exception {
       List<Movie> list = null;
       Movie movie = null;

       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;

       final String SQL = "select id, title, description, rating, image, status, " +
    		   		   " FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, "+ 
    		   		   " FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo " + 
       				   " from   t_movie  " + 
       				   " order by id asc ";

       try {
           Session session = sessionFactory.getCurrentSession();
			
		   list = session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(Movie.class))
					.list();

       } catch (final Exception e) {

           throw e;
       } finally {
           // clean up
           movie = null;

           try {
               DatabaseUtility.clear(conn, pstmt, rs);
           } catch (final Exception ex) {
               // do nothing
           }
       }

       return list;
   }
	
	
	/**
	 * getListMovie
	 * 
	 * @return List<Movie>
	 * @throws Exception
	 */
	public Movie getMovieById(Integer id) throws Exception {
       Movie movie = null;

       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;

       final String SQL = "select id, title, description, rating, image, status, " +
		    		   "FORMAT(created_dt,'yyyy-MM-dd hh:mm:ss') as createdDt, created_user_no as createdUserNo, "+ 
			   		   "FORMAT(updated_dt,'yyyy-MM-dd hh:mm:ss') as updatedDt, updated_user_no as updatedUserNo " +        				   
		    		   "from   t_movie  " + 
       				   "where   id = :id  " + 
       				   "order by id asc ";

       try {
    	   movie = new Movie();
    	   
    	   Session session = sessionFactory.getCurrentSession();
			
    	   movie = (Movie) session.createSQLQuery(SQL)
					.setResultTransformer(Transformers.aliasToBean(Movie.class))
					.setInteger("id", id)
					.uniqueResult();

       } catch (final Exception e) {

           throw e;
       } finally {
    	   
           try {
               DatabaseUtility.clear(conn, pstmt, rs);
           } catch (final Exception ex) {
               // do nothing
           }
       }

       return movie;
   }
	
   public ReturnMessege insertMovie(Movie movie)
		throws Exception {
			Session session =  sessionFactory.getCurrentSession();
			ReturnMessege rm = new ReturnMessege();
			Boolean isSuccess = true;
			String msg = "Successfuly added data";
			Transaction tx = null;
			
			String SQL = "INSERT INTO t_movie " +
					   "(title, description, rating, " +
					   "image, status, created_dt, created_user_no) " +
					   "VALUES " +
					   "(:title, :description, :rating, " +
					   ":image, :status, (CAST(:created_dt AS DateTime)), :created_user_no)";
			try {
				tx = session.beginTransaction();
				
				session.createSQLQuery(SQL)
				.setString("title", movie.getTitle())
				.setString("description", movie.getDescription())
				.setDouble("rating", movie.getRating())
				.setString("image", movie.getImage())
				.setString("status", movie.getStatus())
				.setString("created_dt", movie.getCreatedDt())
				.setInteger("created_user_no", movie.getCreatedUserNo())
				.executeUpdate();
				
				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				if (tx!=null) tx.rollback();
				isSuccess = false;
				msg = e.getMessage();
			}
			
			rm.setIsSuccess(isSuccess);
			rm.setMessege(msg);
			rm.setData(movie);

			return rm;
		}

	public ReturnMessege updateMovie(Integer id, Movie movie)
		throws Exception {
			Session session =  sessionFactory.getCurrentSession();
			ReturnMessege rm = new ReturnMessege();
			Boolean isSuccess = true;
			String msg = "Successfuly updated data";
			Transaction tx = null;

			String SQL = "UPDATE t_movie " + 
					   "SET    " + 
					   "	   title = :title, " + 
					   "	   description = :description, " + 
					   "	   rating = :rating, " + 
					   "       image = :image, " + 
					   "       status = :status, " +
					   "       updated_dt = :updated_dt, " +
					   "       updated_user_no = :updated_user_no " +
					   "WHERE  id = :id";
			try {
				tx = session.beginTransaction();
				
				session.createSQLQuery(SQL)
				.setInteger("id", id)
				.setString("title", movie.getTitle())
				.setString("description", movie.getDescription())
				.setDouble("rating", movie.getRating())
				.setString("image", movie.getImage())
				.setString("status", movie.getStatus())
				.setString("updated_dt", movie.getUpdatedDt())
				.setInteger("updated_user_no", movie.getUpdatedUserNo())

				.executeUpdate();
				
				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				if (tx!=null) tx.rollback();
				isSuccess = false;
				msg = e.getMessage();
			}
			
			rm.setIsSuccess(isSuccess);
			rm.setMessege(msg);
			rm.setData(movie);
			return rm;
		}
	
	
	public ReturnMessege removeMovie(Integer id)
			throws Exception {
				Session session =  sessionFactory.getCurrentSession();
				ReturnMessege rm = new ReturnMessege();
				Boolean isSuccess = true;
				String msg = "Successfuly deleted data";
				Transaction tx = null;

				String SQL = "DELETE t_movie WHERE id = :id";
				try {
					tx = session.beginTransaction();
					
					session.createSQLQuery(SQL)
					.setInteger("id", id)
					.executeUpdate();
					
					tx.commit();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					if (tx!=null) tx.rollback();
					isSuccess = false;
					msg = e.getMessage();
				}
				
				rm.setIsSuccess(isSuccess);
				rm.setMessege(msg);
				rm.setData(id);

				return rm;
			}
}

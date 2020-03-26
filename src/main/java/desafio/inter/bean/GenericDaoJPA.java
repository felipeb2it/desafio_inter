package desafio.inter.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;

import desafio.inter.model.EntidadeBase;


@Stateless
public class GenericDaoJPA {
	

	public enum MatchMode { START, END, EXACT, ANYWHERE }
	
	public enum Order {

		ASC, DESC;

		
		public boolean isAscOrder() {
			return ASC.equals(this);
		}
	}

	
	@PersistenceContext(unitName = "desafio-inter")
	private EntityManager entityManager;


	public <T extends EntidadeBase<PK>, PK extends Serializable> PK save(T entity)  {
		entityManager.persist(entity);
		return entity.getId();
	}



	public <T extends EntidadeBase<PK>, PK extends Serializable> T merge(T entity) {
		return entityManager.merge(entity);
	}


	public <T extends EntidadeBase<?>> void delete(Class<T> clazz, Serializable id) {
		T entity = find(clazz, id);
		if (entity != null) {
			entityManager.remove(entity);
		} else {
			throw new NotFoundException();
		}
	}


	public <T extends EntidadeBase<?>> T find(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}


	public <T extends EntidadeBase<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		
		if(value==null) {
			cq.where(cb.isNull(root.get(propertyName)));
		}else {
			cq.where(cb.equal(root.get(propertyName), value));
		}
		
		return entityManager.createQuery(cq).getResultList();
	}
		

	public <T extends EntidadeBase<?>> List<T> findByPropertys(Class<T> clazz, List<String> propertysName, List<Object> values) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		
		List<Predicate> predicates  = new ArrayList<Predicate>();		
		for(int i=0; i<propertysName.size(); i++) {
			if(values.get(i)!=null && values.get(i)!="" && !values.get(i).toString().isEmpty()) {				
				predicates.add( cb.equal(root.get(propertysName.get(i)), values.get(i)) );
			}						
		}
		
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		return entityManager.createQuery(cq).getResultList();
	}
	

	public <T extends EntidadeBase<?>> List<T> findByPropertysIn(Class<T> clazz, List<String> propertysName, List<Object> values) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		
		List<Predicate> predicates  = new ArrayList<Predicate>();		
		for(int i=0; i<propertysName.size(); i++) {
			predicates.add( root.get(propertysName.get(i)).in(values.get(i)) );			
		}
		
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		return entityManager.createQuery(cq).getResultList();
	}
	

	public <T extends EntidadeBase<?>, X extends EntidadeBase<?>> List<T> findByPropertyIn(Class<T> clazz, String propertyName,
			List<Object> value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(root.get(propertyName).in(value));
		return entityManager.createQuery(cq).getResultList();
	}
	

	public <T extends EntidadeBase<?>> T findFirstByProperty(Class<T> clazz, String propertyName, Object value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.equal(root.get(propertyName), value));
		
		List<T> list = entityManager.createQuery(cq).getResultList();
		
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	

}

-- --------------------------------------------------------
-- 호스트:                          3.36.113.134
-- 서버 버전:                        10.6.5-MariaDB-1:10.6.5+maria~focal - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- roughcode 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `roughcode` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `roughcode`;

-- 테이블 roughcode.BATCH_JOB_EXECUTION 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime(6) NOT NULL,
  `START_TIME` datetime(6) DEFAULT NULL,
  `END_TIME` datetime(6) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime(6) DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_JOB_EXECUTION:~35 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`, `VERSION`, `JOB_INSTANCE_ID`, `CREATE_TIME`, `START_TIME`, `END_TIME`, `STATUS`, `EXIT_CODE`, `EXIT_MESSAGE`, `LAST_UPDATED`, `JOB_CONFIGURATION_LOCATION`) VALUES
	(1, 2, 1, '2023-05-04 04:00:00.028000', '2023-05-04 04:00:00.084000', '2023-05-04 04:00:00.617000', 'COMPLETED', 'COMPLETED', '', '2023-05-04 04:00:00.617000', NULL),
	(2, 2, 2, '2023-05-04 04:00:00.030000', '2023-05-04 04:00:00.086000', '2023-05-04 04:00:00.499000', 'COMPLETED', 'COMPLETED', '', '2023-05-04 04:00:00.504000', NULL),
	(3, 2, 3, '2023-05-05 04:00:00.027000', '2023-05-05 04:00:00.086000', '2023-05-05 04:00:00.437000', 'COMPLETED', 'COMPLETED', '', '2023-05-05 04:00:00.438000', NULL),
	(4, 2, 4, '2023-05-05 04:00:00.041000', '2023-05-05 04:00:00.097000', '2023-05-05 04:00:00.513000', 'COMPLETED', 'COMPLETED', '', '2023-05-05 04:00:00.514000', NULL),
	(5, 2, 5, '2023-05-05 04:00:00.088000', '2023-05-05 04:00:00.178000', '2023-05-05 04:00:00.648000', 'COMPLETED', 'COMPLETED', '', '2023-05-05 04:00:00.652000', NULL),
	(6, 2, 6, '2023-05-06 04:00:00.014000', '2023-05-06 04:00:00.024000', '2023-05-06 04:00:00.078000', 'COMPLETED', 'COMPLETED', '', '2023-05-06 04:00:00.079000', NULL),
	(7, 2, 8, '2023-05-06 04:00:00.103000', '2023-05-06 04:00:00.159000', '2023-05-06 04:00:00.455000', 'COMPLETED', 'COMPLETED', '', '2023-05-06 04:00:00.461000', NULL),
	(8, 2, 9, '2023-05-07 04:00:00.013000', '2023-05-07 04:00:00.022000', '2023-05-07 04:00:00.078000', 'COMPLETED', 'COMPLETED', '', '2023-05-07 04:00:00.079000', NULL),
	(9, 2, 11, '2023-05-08 04:00:00.014000', '2023-05-08 04:00:00.022000', '2023-05-08 04:00:00.073000', 'COMPLETED', 'COMPLETED', '', '2023-05-08 04:00:00.074000', NULL),
	(10, 2, 13, '2023-05-09 04:00:00.025000', '2023-05-09 04:00:00.081000', '2023-05-09 04:00:00.438000', 'COMPLETED', 'COMPLETED', '', '2023-05-09 04:00:00.438000', NULL),
	(11, 2, 15, '2023-05-10 04:00:00.030000', '2023-05-10 04:00:00.086000', '2023-05-10 04:00:00.452000', 'COMPLETED', 'COMPLETED', '', '2023-05-10 04:00:00.453000', NULL),
	(12, 2, 16, '2023-05-10 04:00:00.038000', '2023-05-10 04:00:00.094000', '2023-05-10 04:00:00.658000', 'COMPLETED', 'COMPLETED', '', '2023-05-10 04:00:00.659000', NULL),
	(13, 2, 17, '2023-05-10 04:00:00.085000', '2023-05-10 04:00:00.180000', '2023-05-10 04:00:00.703000', 'COMPLETED', 'COMPLETED', '', '2023-05-10 04:00:00.708000', NULL),
	(14, 2, 18, '2023-05-11 04:00:00.027000', '2023-05-11 04:00:00.087000', '2023-05-11 04:00:00.477000', 'COMPLETED', 'COMPLETED', '', '2023-05-11 04:00:00.478000', NULL),
	(15, 2, 20, '2023-05-12 04:00:00.024000', '2023-05-12 04:00:00.078000', '2023-05-12 04:00:00.187000', 'COMPLETED', 'COMPLETED', '', '2023-05-12 04:00:00.188000', NULL),
	(16, 2, 22, '2023-05-12 04:00:00.290000', '2023-05-12 04:00:00.429000', '2023-05-12 04:00:01.133000', 'COMPLETED', 'COMPLETED', '', '2023-05-12 04:00:01.138000', NULL),
	(17, 2, 23, '2023-05-12 04:00:00.216000', '2023-05-12 04:00:00.336000', '2023-05-12 04:00:00.904000', 'COMPLETED', 'COMPLETED', '', '2023-05-12 04:00:00.919000', NULL),
	(18, 2, 24, '2023-05-12 04:00:00.147000', '2023-05-12 04:00:00.326000', '2023-05-12 04:00:00.909000', 'COMPLETED', 'COMPLETED', '', '2023-05-12 04:00:00.914000', NULL),
	(19, 2, 25, '2023-05-12 04:09:57.462000', '2023-05-12 04:09:57.683000', '2023-05-12 04:09:58.338000', 'COMPLETED', 'COMPLETED', '', '2023-05-12 04:09:58.344000', NULL),
	(20, 2, 26, '2023-05-13 04:00:00.022000', '2023-05-13 04:00:00.072000', '2023-05-13 04:00:00.164000', 'COMPLETED', 'COMPLETED', '', '2023-05-13 04:00:00.164000', NULL),
	(21, 2, 28, '2023-05-14 04:00:00.011000', '2023-05-14 04:00:00.020000', '2023-05-14 04:00:00.062000', 'COMPLETED', 'COMPLETED', '', '2023-05-14 04:00:00.063000', NULL),
	(22, 2, 30, '2023-05-14 15:33:50.703000', '2023-05-14 15:33:51.019000', '2023-05-14 15:33:52.618000', 'COMPLETED', 'COMPLETED', '', '2023-05-14 15:33:52.622000', NULL),
	(23, 2, 31, '2023-05-14 21:17:18.189000', '2023-05-14 21:17:18.352000', '2023-05-14 21:17:19.191000', 'COMPLETED', 'COMPLETED', '', '2023-05-14 21:17:19.196000', NULL),
	(24, 2, 32, '2023-05-15 04:00:00.012000', '2023-05-15 04:00:00.021000', '2023-05-15 04:00:00.063000', 'COMPLETED', 'COMPLETED', '', '2023-05-15 04:00:00.063000', NULL),
	(25, 2, 33, '2023-05-15 13:27:47.864000', '2023-05-15 13:27:48.654000', '2023-05-15 13:27:52.693000', 'COMPLETED', 'COMPLETED', '', '2023-05-15 13:27:52.797000', NULL),
	(26, 2, 34, '2023-05-16 04:00:00.045000', '2023-05-16 04:00:00.096000', '2023-05-16 04:00:00.178000', 'COMPLETED', 'COMPLETED', '', '2023-05-16 04:00:00.179000', NULL),
	(27, 2, 36, '2023-05-17 04:00:00.033000', '2023-05-17 04:00:00.088000', '2023-05-17 04:00:00.196000', 'COMPLETED', 'COMPLETED', '', '2023-05-17 04:00:00.197000', NULL),
	(28, 2, 37, '2023-05-17 04:00:00.053000', '2023-05-17 04:00:00.108000', '2023-05-17 04:00:00.401000', 'COMPLETED', 'COMPLETED', '', '2023-05-17 04:00:00.401000', NULL),
	(29, 2, 38, '2023-05-17 05:21:53.999000', '2023-05-17 05:21:54.253000', '2023-05-17 05:21:55.472000', 'COMPLETED', 'COMPLETED', '', '2023-05-17 05:21:55.483000', NULL),
	(30, 2, 40, '2023-05-18 04:00:00.053000', '2023-05-18 04:00:00.136000', '2023-05-18 04:00:00.677000', 'FAILED', 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy196.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:597)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-18 04:00:00.678000', NULL),
	(31, 2, 41, '2023-05-18 04:19:16.400000', '2023-05-18 04:19:16.588000', '2023-05-18 04:19:17.989000', 'FAILED', 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:597)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-18 04:19:17.993000', NULL),
	(32, 2, 42, '2023-05-19 04:00:00.034000', '2023-05-19 04:00:00.086000', '2023-05-19 04:00:00.454000', 'FAILED', 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy195.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-19 04:00:00.455000', NULL),
	(33, 2, 44, '2023-05-19 04:00:00.228000', '2023-05-19 04:00:00.385000', '2023-05-19 04:00:01.939000', 'FAILED', 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-19 04:00:01.946000', NULL),
	(34, 2, 45, '2023-05-19 04:00:00.382000', '2023-05-19 04:00:00.537000', '2023-05-19 04:00:01.631000', 'FAILED', 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\r\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\r\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\r\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\r\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\r\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\r\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\r\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\r\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\r\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(Transaction', '2023-05-19 04:00:01.639000', NULL),
	(35, 2, 46, '2023-05-19 04:00:00.145000', '2023-05-19 04:00:00.376000', '2023-05-19 04:00:02.080000', 'FAILED', 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-19 04:00:02.087000', NULL);
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_JOB_EXECUTION_CONTEXT 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_JOB_EXECUTION_CONTEXT:~35 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_CONTEXT` (`JOB_EXECUTION_ID`, `SHORT_CONTEXT`, `SERIALIZED_CONTEXT`) VALUES
	(1, '{"@class":"java.util.HashMap"}', NULL),
	(2, '{"@class":"java.util.HashMap"}', NULL),
	(3, '{"@class":"java.util.HashMap"}', NULL),
	(4, '{"@class":"java.util.HashMap"}', NULL),
	(5, '{"@class":"java.util.HashMap"}', NULL),
	(6, '{"@class":"java.util.HashMap"}', NULL),
	(7, '{"@class":"java.util.HashMap"}', NULL),
	(8, '{"@class":"java.util.HashMap"}', NULL),
	(9, '{"@class":"java.util.HashMap"}', NULL),
	(10, '{"@class":"java.util.HashMap"}', NULL),
	(11, '{"@class":"java.util.HashMap"}', NULL),
	(12, '{"@class":"java.util.HashMap"}', NULL),
	(13, '{"@class":"java.util.HashMap"}', NULL),
	(14, '{"@class":"java.util.HashMap"}', NULL),
	(15, '{"@class":"java.util.HashMap"}', NULL),
	(16, '{"@class":"java.util.HashMap"}', NULL),
	(17, '{"@class":"java.util.HashMap"}', NULL),
	(18, '{"@class":"java.util.HashMap"}', NULL),
	(19, '{"@class":"java.util.HashMap"}', NULL),
	(20, '{"@class":"java.util.HashMap"}', NULL),
	(21, '{"@class":"java.util.HashMap"}', NULL),
	(22, '{"@class":"java.util.HashMap"}', NULL),
	(23, '{"@class":"java.util.HashMap"}', NULL),
	(24, '{"@class":"java.util.HashMap"}', NULL),
	(25, '{"@class":"java.util.HashMap"}', NULL),
	(26, '{"@class":"java.util.HashMap"}', NULL),
	(27, '{"@class":"java.util.HashMap"}', NULL),
	(28, '{"@class":"java.util.HashMap"}', NULL),
	(29, '{"@class":"java.util.HashMap"}', NULL),
	(30, '{"@class":"java.util.HashMap"}', NULL),
	(31, '{"@class":"java.util.HashMap"}', NULL),
	(32, '{"@class":"java.util.HashMap"}', NULL),
	(33, '{"@class":"java.util.HashMap"}', NULL),
	(34, '{"@class":"java.util.HashMap"}', NULL),
	(35, '{"@class":"java.util.HashMap"}', NULL);
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_JOB_EXECUTION_PARAMS 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION_PARAMS` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime(6) DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL,
  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_JOB_EXECUTION_PARAMS:~35 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_PARAMS` (`JOB_EXECUTION_ID`, `TYPE_CD`, `KEY_NAME`, `STRING_VAL`, `DATE_VAL`, `LONG_VAL`, `DOUBLE_VAL`, `IDENTIFYING`) VALUES
	(1, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683140400001, 0, 'Y'),
	(2, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683140400000, 0, 'Y'),
	(3, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683226800001, 0, 'Y'),
	(4, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683226800000, 0, 'Y'),
	(5, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683226800008, 0, 'Y'),
	(6, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683313200000, 0, 'Y'),
	(7, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683313200003, 0, 'Y'),
	(8, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683399600000, 0, 'Y'),
	(9, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683486000000, 0, 'Y'),
	(10, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683572400000, 0, 'Y'),
	(11, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683658800001, 0, 'Y'),
	(12, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683658800000, 0, 'Y'),
	(13, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683658800003, 0, 'Y'),
	(14, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683745200001, 0, 'Y'),
	(15, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683831600001, 0, 'Y'),
	(16, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683831600020, 0, 'Y'),
	(17, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683831600033, 0, 'Y'),
	(18, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683831600012, 0, 'Y'),
	(19, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683832197198, 0, 'Y'),
	(20, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1683918000001, 0, 'Y'),
	(21, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684004400000, 0, 'Y'),
	(22, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684046024983, 0, 'Y'),
	(23, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684066637935, 0, 'Y'),
	(24, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684090800000, 0, 'Y'),
	(25, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684124866801, 0, 'Y'),
	(26, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684177200001, 0, 'Y'),
	(27, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684263600000, 0, 'Y'),
	(28, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684263600001, 0, 'Y'),
	(29, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684268513576, 0, 'Y'),
	(30, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684350000000, 0, 'Y'),
	(31, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684351155351, 0, 'Y'),
	(32, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684436400001, 0, 'Y'),
	(33, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684436400008, 0, 'Y'),
	(34, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684436400024, 0, 'Y'),
	(35, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1684436399906, 0, 'Y');
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_JOB_EXECUTION_SEQ 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_JOB_EXECUTION_SEQ:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_SEQ` (`ID`, `UNIQUE_KEY`) VALUES
	(35, '0');
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_JOB_INSTANCE 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_JOB_INSTANCE:~33 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_JOB_INSTANCE` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`, `VERSION`, `JOB_NAME`, `JOB_KEY`) VALUES
	(1, 0, 'job', '40d020528de015eb6acd60e84a58da96'),
	(2, 0, 'job', 'c25df4108cdce94ec5038143bac499f8'),
	(3, 0, 'job', 'b3935c44bd41b8f4b3ca78bd73d4c7b6'),
	(4, 0, 'job', '6b04a40479a3e9fee44343a31226005a'),
	(5, 0, 'job', 'fa1976eee76eeda37dde75340ba44551'),
	(6, 0, 'job', 'e0668523c96f8f52275e3847705026fc'),
	(8, 0, 'job', '28f1d246506c72cc4f2c9fc4e3d8f2bf'),
	(9, 0, 'job', '679da07ec55c112365b1109cd445bca4'),
	(11, 0, 'job', 'b353a4721d62e1f5d641f523aa3e7595'),
	(13, 0, 'job', 'daaf4b9aea80024beebc08be147de18f'),
	(15, 0, 'job', '96755daa2bf491e96561c30c8f87f8b8'),
	(16, 0, 'job', '4dec3b3fa0606771c108ed158bff817f'),
	(17, 0, 'job', 'f5087a64b72b9f3c8c38d12bd7598aa5'),
	(18, 0, 'job', '4094e9ee42d62fa80456bd7858643f85'),
	(20, 0, 'job', 'd0c0805e56580b909573615bbde82bf7'),
	(22, 0, 'job', 'b9743dcdfaf956e480ce43395de74e69'),
	(23, 0, 'job', 'c4a9dde978a60045e9b2a12b947eb07e'),
	(24, 0, 'job', 'a814e29f065b51b0d687ccadc8dfa5bc'),
	(25, 0, 'job', '8c4bed06699286f5763ba98f8f68aebf'),
	(26, 0, 'job', 'b56eda8aab1957c24785948834d073f2'),
	(28, 0, 'job', '1e13ced1400be7a47ac6ce4abd1f6a07'),
	(30, 0, 'job', '2cdc3c692bbe951aa92cab83500d2466'),
	(31, 0, 'job', '3f4adcbf5d306e0b818c5f7c46729948'),
	(32, 0, 'job', '548d09bc5fa492ceb2193fd894fe3d95'),
	(33, 0, 'job', 'a4c9402c0288d905d3be9610955c9a4e'),
	(34, 0, 'job', '78b0aea8ba3cb073b4e45257d580de67'),
	(36, 0, 'job', 'a6cb75f9ff27dd250c0e434c534d0d87'),
	(37, 0, 'job', 'da312cb4cbe7708aa25efc9b799b3ab5'),
	(38, 0, 'job', 'ac40be1932684a43d195d81ad5bda272'),
	(40, 0, 'job', '0cbfee1e923e298d5fe4aed324acbb80'),
	(41, 0, 'job', 'ac25e9301b225d7b10afc40ca761d06e'),
	(42, 0, 'job', '83cd32a15fca2c16f4cfbeca687c06d7'),
	(44, 0, 'job', 'db0c6389c6b7899c3c46b89186fc4f8b'),
	(45, 0, 'job', '940db667776e9b5f3ec6173ee76c2d37'),
	(46, 0, 'job', 'd485bb1a6bbb87d53c418127996c8587');
/*!40000 ALTER TABLE `BATCH_JOB_INSTANCE` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_JOB_SEQ 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_JOB_SEQ:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_JOB_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_SEQ` (`ID`, `UNIQUE_KEY`) VALUES
	(46, '0');
/*!40000 ALTER TABLE `BATCH_JOB_SEQ` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_STEP_EXECUTION 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_STEP_EXECUTION` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime(6) NOT NULL,
  `END_TIME` datetime(6) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_STEP_EXECUTION:~41 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`, `VERSION`, `STEP_NAME`, `JOB_EXECUTION_ID`, `START_TIME`, `END_TIME`, `STATUS`, `COMMIT_COUNT`, `READ_COUNT`, `FILTER_COUNT`, `WRITE_COUNT`, `READ_SKIP_COUNT`, `WRITE_SKIP_COUNT`, `PROCESS_SKIP_COUNT`, `ROLLBACK_COUNT`, `EXIT_CODE`, `EXIT_MESSAGE`, `LAST_UPDATED`) VALUES
	(1, 3, 'step', 1, '2023-05-04 04:00:00.112000', '2023-05-04 04:00:00.605000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-04 04:00:00.607000'),
	(2, 3, 'step', 2, '2023-05-04 04:00:00.113000', '2023-05-04 04:00:00.486000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-04 04:00:00.488000'),
	(3, 3, 'step', 3, '2023-05-05 04:00:00.113000', '2023-05-05 04:00:00.425000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-05 04:00:00.427000'),
	(4, 3, 'step', 4, '2023-05-05 04:00:00.124000', '2023-05-05 04:00:00.501000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-05 04:00:00.503000'),
	(5, 3, 'step', 5, '2023-05-05 04:00:00.288000', '2023-05-05 04:00:00.612000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-05 04:00:00.616000'),
	(6, 3, 'step', 6, '2023-05-06 04:00:00.044000', '2023-05-06 04:00:00.071000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-06 04:00:00.072000'),
	(7, 3, 'step', 7, '2023-05-06 04:00:00.288000', '2023-05-06 04:00:00.410000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-06 04:00:00.416000'),
	(8, 3, 'step', 8, '2023-05-07 04:00:00.043000', '2023-05-07 04:00:00.067000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-07 04:00:00.068000'),
	(9, 3, 'step', 9, '2023-05-08 04:00:00.044000', '2023-05-08 04:00:00.065000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-08 04:00:00.066000'),
	(10, 3, 'step', 10, '2023-05-09 04:00:00.121000', '2023-05-09 04:00:00.428000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-09 04:00:00.429000'),
	(11, 3, 'step', 11, '2023-05-10 04:00:00.113000', '2023-05-10 04:00:00.443000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-10 04:00:00.444000'),
	(12, 3, 'step', 12, '2023-05-10 04:00:00.120000', '2023-05-10 04:00:00.649000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-10 04:00:00.650000'),
	(13, 3, 'step', 13, '2023-05-10 04:00:00.307000', '2023-05-10 04:00:00.663000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-10 04:00:00.667000'),
	(14, 3, 'step', 14, '2023-05-11 04:00:00.115000', '2023-05-11 04:00:00.467000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-11 04:00:00.468000'),
	(15, 3, 'step', 15, '2023-05-12 04:00:00.104000', '2023-05-12 04:00:00.179000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-12 04:00:00.180000'),
	(16, 3, 'step', 16, '2023-05-12 04:00:00.582000', '2023-05-12 04:00:01.079000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-12 04:00:01.085000'),
	(17, 3, 'step', 17, '2023-05-12 04:00:00.490000', '2023-05-12 04:00:00.842000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-12 04:00:00.848000'),
	(18, 3, 'step', 18, '2023-05-12 04:00:00.500000', '2023-05-12 04:00:00.833000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-12 04:00:00.845000'),
	(19, 3, 'step', 19, '2023-05-12 04:09:57.882000', '2023-05-12 04:09:58.268000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-12 04:09:58.278000'),
	(20, 3, 'step', 20, '2023-05-13 04:00:00.094000', '2023-05-13 04:00:00.150000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-13 04:00:00.157000'),
	(21, 3, 'step', 21, '2023-05-14 04:00:00.038000', '2023-05-14 04:00:00.056000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-14 04:00:00.056000'),
	(22, 3, 'step', 22, '2023-05-14 15:33:51.161000', '2023-05-14 15:33:52.575000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-14 15:33:52.584000'),
	(23, 3, 'step', 23, '2023-05-14 21:17:18.473000', '2023-05-14 21:17:19.153000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-14 21:17:19.158000'),
	(24, 3, 'step', 24, '2023-05-15 04:00:00.039000', '2023-05-15 04:00:00.056000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-15 04:00:00.057000'),
	(25, 3, 'step', 25, '2023-05-15 13:27:50.052000', '2023-05-15 13:27:52.108000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-15 13:27:52.181000'),
	(26, 3, 'step', 26, '2023-05-16 04:00:00.116000', '2023-05-16 04:00:00.169000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-16 04:00:00.169000'),
	(27, 3, 'step', 27, '2023-05-17 04:00:00.110000', '2023-05-17 04:00:00.177000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-17 04:00:00.178000'),
	(28, 3, 'step', 28, '2023-05-17 04:00:00.131000', '2023-05-17 04:00:00.390000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-17 04:00:00.391000'),
	(29, 3, 'step', 29, '2023-05-17 05:21:54.479000', '2023-05-17 05:21:55.411000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-17 05:21:55.428000'),
	(30, 3, 'stepAlarm', 30, '2023-05-18 04:00:00.162000', '2023-05-18 04:00:00.494000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-18 04:00:00.495000'),
	(31, 2, 'stepProject', 30, '2023-05-18 04:00:00.516000', '2023-05-18 04:00:00.667000', 'FAILED', 0, 0, 0, 0, 0, 0, 0, 1, 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy196.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:597)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-18 04:00:00.668000'),
	(32, 3, 'stepAlarm', 31, '2023-05-18 04:19:16.751000', '2023-05-18 04:19:17.569000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-18 04:19:17.579000'),
	(33, 2, 'stepProject', 31, '2023-05-18 04:19:17.719000', '2023-05-18 04:19:17.941000', 'FAILED', 0, 0, 0, 0, 0, 0, 0, 1, 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:597)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-18 04:19:17.947000'),
	(34, 3, 'stepAlarm', 32, '2023-05-19 04:00:00.109000', '2023-05-19 04:00:00.377000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-19 04:00:00.378000'),
	(35, 2, 'stepProject', 32, '2023-05-19 04:00:00.398000', '2023-05-19 04:00:00.445000', 'FAILED', 0, 0, 0, 0, 0, 0, 0, 1, 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy195.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-19 04:00:00.446000'),
	(36, 3, 'stepAlarm', 34, '2023-05-19 04:00:00.662000', '2023-05-19 04:00:01.216000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-19 04:00:01.221000'),
	(37, 3, 'stepAlarm', 33, '2023-05-19 04:00:00.582000', '2023-05-19 04:00:00.904000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-19 04:00:00.952000'),
	(38, 3, 'stepAlarm', 35, '2023-05-19 04:00:00.599000', '2023-05-19 04:00:01.416000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2023-05-19 04:00:01.465000'),
	(39, 2, 'stepProject', 34, '2023-05-19 04:00:01.341000', '2023-05-19 04:00:01.578000', 'FAILED', 0, 0, 0, 0, 0, 0, 0, 1, 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\r\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\r\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\r\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\r\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\r\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\r\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\r\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\r\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\r\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(Transaction', '2023-05-19 04:00:01.583000'),
	(40, 2, 'stepProject', 33, '2023-05-19 04:00:01.492000', '2023-05-19 04:00:01.878000', 'FAILED', 0, 0, 0, 0, 0, 0, 0, 1, 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-19 04:00:01.885000'),
	(41, 2, 'stepProject', 35, '2023-05-19 04:00:01.698000', '2023-05-19 04:00:02.009000', 'FAILED', 0, 0, 0, 0, 0, 0, 0, 1, 'FAILED', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not execute statement\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:259)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:233)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:551)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:152)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:145)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$Proxy208.deleteAllByProjectsList(Unknown Source)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl.deleteExpiredProject(ProjectsServiceImpl.java:591)\n	at com.cody.roughcode.project.service.ProjectsServiceImpl$$FastClassBySpringCGLIB$$19885805.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\n	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123', '2023-05-19 04:00:02.017000');
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_STEP_EXECUTION_CONTEXT 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_STEP_EXECUTION_CONTEXT:~41 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION_CONTEXT` (`STEP_EXECUTION_ID`, `SHORT_CONTEXT`, `SERIALIZED_CONTEXT`) VALUES
	(1, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1328/0x0000000840b79440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(2, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1328/0x0000000840b79440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(3, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1331/0x0000000840b7ec40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(4, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1337/0x0000000840b74440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(5, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1327/0x000000080154e498","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(6, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1337/0x0000000840b74440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(7, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1327/0x000000080154e498","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(8, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1331/0x0000000840b7ec40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(9, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1331/0x0000000840b7ec40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(10, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1331/0x0000000840b86c40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(11, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1337/0x0000000840b73040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(12, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1331/0x0000000840b86840","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(13, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1326/0x00000008015630a8","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(14, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1331/0x0000000840b86040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(15, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1332/0x0000000840b7c440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(16, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1316/0x0000000800b08440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(17, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1271/0x0000000800bd2840","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(18, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1314/0x0000000800a9a840","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(19, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1286/0x0000000800c20840","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(20, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1332/0x0000000840b7bc40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(21, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1332/0x0000000840b7bc40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(22, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1282/0x0000000100bc9440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(23, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1317/0x0000000800ae8440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(24, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1332/0x0000000840b7bc40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(25, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1286/0x0000000800c20440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(26, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1333/0x0000000840bc8040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(27, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1344/0x0000000840be5040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(28, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1337/0x0000000840be9440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(29, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1287/0x0000000100c4c440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(30, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1337/0x0000000840beec40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(31, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1338/0x0000000840bee040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(32, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1292/0x0000000100c59040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(33, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1293/0x0000000100c59440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(34, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1337/0x0000000840bf2840","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(35, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1338/0x0000000840bf2c40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(36, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1323/0x0000000800b41c40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(37, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1287/0x0000000100c76c40","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(38, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1292/0x0000000800c76040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(39, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1324/0x0000000800b41040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(40, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1288/0x0000000100c76040","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(41, '{"@class":"java.util.HashMap","batch.taskletType":"com.cody.roughcode.config.BatchConfig$$Lambda$1293/0x0000000800c76440","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL);
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` ENABLE KEYS */;

-- 테이블 roughcode.BATCH_STEP_EXECUTION_SEQ 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.BATCH_STEP_EXECUTION_SEQ:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION_SEQ` (`ID`, `UNIQUE_KEY`) VALUES
	(41, '0');
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` ENABLE KEYS */;

-- 테이블 roughcode.codes 구조 내보내기
CREATE TABLE IF NOT EXISTS `codes` (
  `codes_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `like_cnt` int(11) DEFAULT NULL,
  `num` bigint(20) NOT NULL,
  `review_cnt` int(11) DEFAULT NULL,
  `title` varchar(63) NOT NULL,
  `version` int(11) NOT NULL,
  `code_writer_id` bigint(20) unsigned NOT NULL,
  `projects_id` bigint(20) unsigned DEFAULT NULL,
  `expire_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`codes_id`),
  KEY `FKa2tqs2l3jq2u1xl6jsl1gotha` (`code_writer_id`),
  KEY `FKoxmg7duq3x1iy9p9y7bftd1ln` (`projects_id`),
  CONSTRAINT `FKa2tqs2l3jq2u1xl6jsl1gotha` FOREIGN KEY (`code_writer_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKoxmg7duq3x1iy9p9y7bftd1ln` FOREIGN KEY (`projects_id`) REFERENCES `projects` (`projects_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.codes:~55 rows (대략적) 내보내기
/*!40000 ALTER TABLE `codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `codes` ENABLE KEYS */;

-- 테이블 roughcode.codes_info 구조 내보내기
CREATE TABLE IF NOT EXISTS `codes_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `content` text DEFAULT NULL,
  `favorite_cnt` int(11) DEFAULT NULL,
  `github_url` varchar(1000) NOT NULL,
  `codes_id` bigint(20) unsigned NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  `github_api_url` varchar(255) DEFAULT NULL,
  `languages_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfgsys4y763p3s9tj18tp2irbc` (`codes_id`),
  CONSTRAINT `FKfgsys4y763p3s9tj18tp2irbc` FOREIGN KEY (`codes_id`) REFERENCES `codes` (`codes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.codes_info:~68 rows (대략적) 내보내기
/*!40000 ALTER TABLE `codes_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `codes_info` ENABLE KEYS */;

-- 테이블 roughcode.code_favorites 구조 내보내기
CREATE TABLE IF NOT EXISTS `code_favorites` (
  `favorites_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `content` text DEFAULT NULL,
  `codes_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`favorites_id`),
  KEY `FKa51p99ujq6pq4x0311o34fhg7` (`codes_id`),
  KEY `FKqqaoavsp6nkkokkcrqhr9psim` (`users_id`),
  CONSTRAINT `FKa51p99ujq6pq4x0311o34fhg7` FOREIGN KEY (`codes_id`) REFERENCES `codes` (`codes_id`),
  CONSTRAINT `FKqqaoavsp6nkkokkcrqhr9psim` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.code_favorites:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `code_favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `code_favorites` ENABLE KEYS */;

-- 테이블 roughcode.code_languages 구조 내보내기
CREATE TABLE IF NOT EXISTS `code_languages` (
  `languages_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cnt` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`languages_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.code_languages:~88 rows (대략적) 내보내기
/*!40000 ALTER TABLE `code_languages` DISABLE KEYS */;
INSERT INTO `code_languages` (`languages_id`, `cnt`, `name`) VALUES
	(1, 6, 'plaintext'),
	(2, 0, 'abap'),
	(3, 1, 'apex'),
	(4, 0, 'azcli'),
	(5, 0, 'bat'),
	(6, 0, 'bicep'),
	(7, 0, 'cameligo'),
	(8, 0, 'clojure'),
	(9, 1, 'coffeescript'),
	(10, 0, 'c'),
	(11, 1, 'cpp'),
	(12, 0, 'csharp'),
	(13, 0, 'csp'),
	(14, 0, 'css'),
	(15, 0, 'cypher'),
	(16, 0, 'dart'),
	(17, 0, 'dockerfile'),
	(18, 0, 'ecl'),
	(19, 0, 'elixir'),
	(20, 0, 'flow9'),
	(21, 0, 'fsharp'),
	(22, 0, 'freemarker2'),
	(23, 0, 'freemarker2.tag-angle.interpolation-dollar'),
	(24, 0, 'freemarker2.tag-bracket.interpolation-dollar'),
	(25, 0, 'freemarker2.tag-angle.interpolation-bracket'),
	(26, 0, 'freemarker2.tag-bracket.interpolation-bracket'),
	(27, 0, 'freemarker2.tag-auto.interpolation-dollar'),
	(28, 0, 'freemarker2.tag-auto.interpolation-bracket'),
	(29, 0, 'go'),
	(30, 0, 'graphql'),
	(31, 0, 'handlebars'),
	(32, 0, 'hcl'),
	(33, 0, 'html'),
	(34, 0, 'ini'),
	(35, 0, 'java'),
	(36, 1, 'javascript'),
	(37, 0, 'julia'),
	(38, 0, 'kotlin'),
	(39, 0, 'less'),
	(40, 0, 'lexon'),
	(41, 0, 'lua'),
	(42, 0, 'liquid'),
	(43, 0, 'm3'),
	(44, 0, 'markdown'),
	(45, 0, 'mips'),
	(46, 0, 'msdax'),
	(47, 0, 'mysql'),
	(48, 0, 'objective-c'),
	(49, 0, 'pascal'),
	(50, 0, 'pascaligo'),
	(51, 0, 'perl'),
	(52, 0, 'pgsql'),
	(53, 0, 'php'),
	(54, 0, 'pla'),
	(55, 0, 'postiats'),
	(56, 0, 'powerquery'),
	(57, 0, 'powershell'),
	(58, 0, 'proto'),
	(59, 0, 'pug'),
	(60, 2, 'python'),
	(61, 0, 'qsharp'),
	(62, 0, 'r'),
	(63, 0, 'razor'),
	(64, 0, 'redis'),
	(65, 0, 'redshift'),
	(66, 0, 'restructuredtext'),
	(67, 0, 'ruby'),
	(68, 0, 'rust'),
	(69, 0, 'sb'),
	(70, 0, 'scala'),
	(71, 0, 'scheme'),
	(72, 0, 'scss'),
	(73, 0, 'shell'),
	(74, 0, 'sol'),
	(75, 0, 'aes'),
	(76, 0, 'sparql'),
	(77, 0, 'sql'),
	(78, 0, 'st'),
	(79, 0, 'swift'),
	(80, 0, 'systemverilog'),
	(81, 0, 'verilog'),
	(82, 0, 'tcl'),
	(83, 0, 'twig'),
	(84, 0, 'typescript'),
	(85, 0, 'vb'),
	(86, 0, 'xml'),
	(87, 0, 'yaml'),
	(88, 0, 'json');
/*!40000 ALTER TABLE `code_languages` ENABLE KEYS */;

-- 테이블 roughcode.code_likes 구조 내보내기
CREATE TABLE IF NOT EXISTS `code_likes` (
  `likes_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `codes_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`likes_id`),
  KEY `FKdbwps4k22b65bmlgtsqq94f9q` (`codes_id`),
  KEY `FKlxvrmpw644975je6tal8pfulo` (`users_id`),
  CONSTRAINT `FKdbwps4k22b65bmlgtsqq94f9q` FOREIGN KEY (`codes_id`) REFERENCES `codes` (`codes_id`),
  CONSTRAINT `FKlxvrmpw644975je6tal8pfulo` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.code_likes:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `code_likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `code_likes` ENABLE KEYS */;

-- 테이블 roughcode.code_selected_tags 구조 내보내기
CREATE TABLE IF NOT EXISTS `code_selected_tags` (
  `selected_tags_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `codes_id` bigint(20) unsigned NOT NULL,
  `tags_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`selected_tags_id`),
  KEY `FKr8t31qn9umv0k03xc5pvtypip` (`codes_id`),
  KEY `FKeqd2srcef1vj7k5v0ytx4b1mu` (`tags_id`),
  CONSTRAINT `FKeqd2srcef1vj7k5v0ytx4b1mu` FOREIGN KEY (`tags_id`) REFERENCES `code_tags` (`tags_id`),
  CONSTRAINT `FKr8t31qn9umv0k03xc5pvtypip` FOREIGN KEY (`codes_id`) REFERENCES `codes` (`codes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.code_selected_tags:~109 rows (대략적) 내보내기
/*!40000 ALTER TABLE `code_selected_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `code_selected_tags` ENABLE KEYS */;

-- 테이블 roughcode.code_tags 구조 내보내기
CREATE TABLE IF NOT EXISTS `code_tags` (
  `tags_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cnt` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`tags_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.code_tags:~99 rows (대략적) 내보내기
/*!40000 ALTER TABLE `code_tags` DISABLE KEYS */;
INSERT INTO `code_tags` (`tags_id`, `cnt`, `name`) VALUES
	(1, 5, 'C/C++'),
	(2, 2, 'Java'),
	(3, 2, 'Python'),
	(4, 1, 'JavaScript'),
	(5, 0, 'Ruby'),
	(6, 0, 'PHP'),
	(7, 0, 'Swift'),
	(8, 0, 'Kotlin'),
	(9, 0, 'Go'),
	(10, 0, 'Rust'),
	(11, 3, 'C#'),
	(12, 0, 'TypeScript'),
	(13, 0, 'Perl'),
	(14, 0, 'Scala'),
	(15, 0, 'Dart'),
	(16, 0, 'Lua'),
	(17, 0, 'R'),
	(18, 0, 'Shell'),
	(19, 0, 'SQL'),
	(20, 0, 'HTML'),
	(21, 1, 'CSS'),
	(22, 0, 'XML'),
	(23, 0, 'JSON'),
	(24, 0, 'YAML'),
	(25, 0, 'Markdown'),
	(26, 0, 'LaTeX'),
	(27, 1, 'Assembly'),
	(28, 0, 'Objective-C'),
	(29, 4, 'Bash'),
	(30, 0, 'PowerShell'),
	(31, 1, 'CoffeeScript'),
	(32, 0, 'VBScript'),
	(33, 4, 'Ada'),
	(34, 3, 'Cobol'),
	(35, 0, 'Fortran'),
	(36, 0, 'Lisp'),
	(37, 0, 'Prolog'),
	(38, 0, 'VHDL'),
	(39, 0, 'Verilog'),
	(40, 0, 'MATLAB'),
	(41, 0, 'Julia'),
	(42, 0, 'Haskell'),
	(43, 0, 'Groovy'),
	(44, 1, 'Clojure'),
	(45, 0, 'F#'),
	(46, 1, 'Elixir'),
	(47, 0, 'Erlang'),
	(48, 1, 'Crystal');
/*!40000 ALTER TABLE `code_tags` ENABLE KEYS */;

-- 테이블 roughcode.feedbacks 구조 내보내기
CREATE TABLE IF NOT EXISTS `feedbacks` (
  `feedbacks_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `complained` datetime DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `complaint` text DEFAULT NULL,
  `content` text DEFAULT NULL,
  `like_cnt` int(11) DEFAULT NULL,
  `selected` int(11) DEFAULT NULL,
  `id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`feedbacks_id`),
  KEY `FKbdtrkrrpaejsm2v1nxsoxpo1v` (`id`),
  KEY `FKdf0gygvywalbg1a6lta0u8g7m` (`users_id`),
  CONSTRAINT `FKbdtrkrrpaejsm2v1nxsoxpo1v` FOREIGN KEY (`id`) REFERENCES `projects_info` (`id`),
  CONSTRAINT `FKdf0gygvywalbg1a6lta0u8g7m` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.feedbacks:~124 rows (대략적) 내보내기
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
INSERT INTO `feedbacks` (`feedbacks_id`, `complained`, `created_date`, `modified_date`, `complaint`, `content`, `like_cnt`, `selected`, `id`, `users_id`) VALUES
	(1, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '와.. 진짜 필요했어요', 0, 0, 10, 2),
	(2, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '진짜 잘만들었네요. 근데 피드백 수정이 잘 안돼요', 0, 1, 10, 3),
	(3, '2023-05-19 02:59:38', '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '욕설 심한 욕설', 0, 0, 10, 1),
	(4, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '깔끔합니다', 0, 0, 10, NULL),
	(5, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', 'shitf+enter누르면 엔터되게 하고 enter만 누르면 입력되게하면 어떤가요? 피드백 말하는거임', 0, 0, 10, 2),
	(6, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '강아지랑 새 너무 귀엽잖아??ㅠㅠㅠㅠ', 0, 0, 11, NULL),
	(7, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '개랑 새 인형 만들어줘요', 0, 0, 11, 2),
	(8, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '이게 그 소문의 토이프로젝트 공유 사이트?', 0, 0, 12, NULL),
	(9, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '<p>소문 듣고 찾아왔습니다.</p> <p>개발새발에 개발새발이 올라와있네요?! ㅋㅋㅋㅋㅋ</p>', 0, 0, 12, 2),
	(10, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '항상 피드백을 반영해주셔서 감사합니다', 0, 0, 12, 3),
	(11, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', '욕설 심한 욕설', 0, 0, 12, NULL),
	(12, NULL, '2023-05-19 02:59:38.000000', '2023-05-19 02:59:38.000000', '', 'apk도 가능하면 좋겠어요', 0, 0, 12, 2),
	(13, NULL, '2023-05-19 02:59:39.000000', '2023-05-19 02:59:39.000000', '', '이거 뭐지? 개발새발은 올라와있는 글이 있지 않나요??', 0, 0, 37, NULL);
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;

-- 테이블 roughcode.feedbacks_complains 구조 내보내기
CREATE TABLE IF NOT EXISTS `feedbacks_complains` (
  `feedbakcs_complains_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `feedbacks_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`feedbakcs_complains_id`),
  KEY `FKnelb2sg6cjjd5gny92a2l3b73` (`feedbacks_id`),
  KEY `FKfw06hpn9i3g5ybfr5d6vvid0g` (`users_id`),
  CONSTRAINT `FKfw06hpn9i3g5ybfr5d6vvid0g` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKnelb2sg6cjjd5gny92a2l3b73` FOREIGN KEY (`feedbacks_id`) REFERENCES `feedbacks` (`feedbacks_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.feedbacks_complains:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `feedbacks_complains` DISABLE KEYS */;
INSERT INTO `feedbacks_complains` (`feedbakcs_complains_id`, `feedbacks_id`, `users_id`) VALUES
	(1, 11, 6),
	(2, 11, 3),
	(3, 11, 4),
	(4, 11, 5);
/*!40000 ALTER TABLE `feedbacks_complains` ENABLE KEYS */;

-- 테이블 roughcode.feedbacks_likes 구조 내보내기
CREATE TABLE IF NOT EXISTS `feedbacks_likes` (
  `feedbakcs_likes_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `feedbacks_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`feedbakcs_likes_id`),
  KEY `FKqdxtkusaq26psg31y3g7ywvse` (`feedbacks_id`),
  KEY `FKfgh2nhuydne475ymmnk6qtmra` (`users_id`),
  CONSTRAINT `FKfgh2nhuydne475ymmnk6qtmra` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKqdxtkusaq26psg31y3g7ywvse` FOREIGN KEY (`feedbacks_id`) REFERENCES `feedbacks` (`feedbacks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.feedbacks_likes:~9 rows (대략적) 내보내기
/*!40000 ALTER TABLE `feedbacks_likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedbacks_likes` ENABLE KEYS */;

-- 테이블 roughcode.projects 구조 내보내기
CREATE TABLE IF NOT EXISTS `projects` (
  `projects_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `closed` bit(1) DEFAULT NULL,
  `feedback_cnt` int(11) DEFAULT NULL,
  `img` varchar(255) NOT NULL,
  `introduction` varchar(255) NOT NULL,
  `like_cnt` int(11) DEFAULT NULL,
  `num` bigint(20) NOT NULL,
  `title` varchar(20) NOT NULL,
  `version` int(11) NOT NULL,
  `project_writer_id` bigint(20) unsigned NOT NULL,
  `expire_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`projects_id`),
  KEY `FK6i13eh0vnfy08ctopdn2hvkt4` (`project_writer_id`),
  CONSTRAINT `FK6i13eh0vnfy08ctopdn2hvkt4` FOREIGN KEY (`project_writer_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.projects:~104 rows (대략적) 내보내기
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` (`projects_id`, `created_date`, `modified_date`, `closed`, `feedback_cnt`, `img`, `introduction`, `like_cnt`, `num`, `title`, `version`, `project_writer_id`, `expire_date`) VALUES
	(1, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_1_1', '주사위 굴리는거 한번 만들어봤는데..', 0, 1, '주사위 굴리기', 1, 2, NULL),
	(2, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_2_1', '소개합니다', 0, 2, '사다리타기', 1, 2, NULL),
	(3, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_2_2', '사다리 타기 해보세요!!', 0, 2, '사다리타기', 2, 2, NULL),
	(4, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_2_3', '간단한 사다리타기입니다', 0, 2, '사다리타기', 3, 2, NULL),
	(5, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_3_1', '소개합니다', 0, 3, '단순 게시판', 1, 2, NULL),
	(6, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_3_2', '게시판 만들어봤는데..', 0, 3, '단순 게시판', 2, 2, NULL),
	(7, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_4_1', '소개합니다', 0, 4, 'todo list', 1, 2, NULL),
	(8, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_4_2', 'todo list 입니다. 피드백 부탁드려요', 0, 4, 'todo list', 2, 2, NULL),
	(9, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_5_1', '건강한 자세 습관화를 위한 사이트', 0, 5, 'SHabit', 1, 2, NULL),
	(10, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 5, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_2_1', '소개합니다', 0, 2, '개발새발', 1, 1, NULL),
	(11, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 2, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_2_2', '개발새발인 프로젝트 우리 눈에는 이쁘다! 많이 이용해주세요', 0, 2, '개발새발', 2, 1, NULL),
	(12, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'0', 4, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_2_3', '개발새발인 프로젝트 우리 눈에는 이쁘다! 와서 토이프로젝트 공유하고 피드백 받아보세요!', 0, 2, '개발새발', 3, 1, NULL),
	(13, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_7_1', '눈 건강을 위한 사이트', 0, 7, 'EHabit', 1, 2, NULL),
	(14, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_3_1', '면접 준비하기 힘드셨죠?', 0, 3, '면접 준비 사이트', 1, 1, NULL),
	(15, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_3_2', '면접 준비하러 들어오세요', 0, 3, '면접 준비 사이트', 2, 1, NULL),
	(16, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_9_1', '추천 사이트입니다', 0, 9, '냉장고 털자', 1, 2, NULL),
	(17, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_9_2', '추천 사이트입니다', 0, 9, '냉장고 털자', 2, 2, NULL),
	(18, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_9_3', '추천 사이트입니다', 0, 9, '냉장고 털자', 3, 2, NULL),
	(19, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_9_4', '냉장고 음식 재료로 레시피를 추천해드려요', 0, 9, '냉장고 털자', 4, 2, NULL),
	(20, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_10_1', '그냥 만들어본 사이트. 로그인만 확인해주세요ㅠ', 0, 10, '로그인', 1, 2, NULL),
	(21, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_11_1', '소개합니다', 0, 11, '투두', 1, 2, NULL),
	(22, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_11_2', 'todo list 입니다. 그냥 또 재미로 ㅎㅎ', 0, 11, '투두', 2, 2, NULL),
	(23, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_4_1', '소개합니다', 0, 4, '관광지 추천', 1, 1, NULL),
	(24, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_4_2', '장소 추천 사이트', 0, 4, '관광지 추천', 2, 1, NULL),
	(25, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_4_3', '장소 추천해드려요', 0, 4, '관광지 추천', 3, 1, NULL),
	(26, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_4_4', '장소 추천 사이트', 0, 4, '관광지 추천', 4, 1, NULL),
	(27, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_4_5', '관광지 추천', 0, 4, '관광지 추천', 5, 1, NULL),
	(28, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_13_1', '포트폴리오사이트인데 괜찮나요?', 0, 13, '포폴', 1, 2, NULL),
	(29, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_14_1', '여행지 추천', 0, 14, '여행가자', 1, 2, NULL),
	(30, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_15_1', '소개합니다', 0, 15, '랜덤 사이트', 1, 2, NULL),
	(31, '2023-05-19 02:59:32.000000', '2023-05-19 02:59:32.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_15_2', '랜덤한 게임을 즐겨봅시다', 0, 15, '랜덤 사이트', 2, 2, NULL),
	(32, '2023-05-19 02:59:33.000000', '2023-05-19 02:59:33.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_16_1', '따릉이 보조 사이트', 0, 16, 'Ttarawa', 1, 2, NULL),
	(33, '2023-05-19 02:59:33.000000', '2023-05-19 02:59:33.000000', b'0', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_17_1', '춘식이 사진 모음 사이트', 0, 17, '춘식이 최고', 1, 2, NULL),
	(34, '2023-05-19 02:59:33.000000', '2023-05-19 02:59:33.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_5_1', '소개합니다', 0, 5, 'todo list', 1, 1, NULL),
	(35, '2023-05-19 02:59:33.000000', '2023-05-19 02:59:33.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/hani2057_5_2', 'todo list 오랜만에 연습해봤어요. 피드백 부탁드려요', 0, 5, 'todo list', 2, 1, NULL),
	(36, '2023-05-19 02:59:33.000000', '2023-05-19 02:59:33.000000', b'1', 0, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_19_1', '화장품 추천해주는 사이트입니다. 피드백 받고싶어요', 0, 19, '화장품 추천', 1, 2, NULL),
	(37, '2023-05-19 02:59:33.000000', '2023-05-19 02:59:33.000000', b'0', 1, 'https://d2swdwg2kwda2j.cloudfront.net/project/kosy318_20_1', '소개합니다', 0, 20, '개발새발', 1, 2, NULL);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;

-- 테이블 roughcode.projects_info 구조 내보내기
CREATE TABLE IF NOT EXISTS `projects_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `closed_checked` datetime(6) DEFAULT NULL,
  `complaint` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `favorite_cnt` int(11) DEFAULT NULL,
  `notice` text NOT NULL,
  `url` varchar(255) NOT NULL,
  `projects_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoj7fmrasfty36b0yufv1f8c7b` (`projects_id`),
  CONSTRAINT `FKoj7fmrasfty36b0yufv1f8c7b` FOREIGN KEY (`projects_id`) REFERENCES `projects` (`projects_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.projects_info:~106 rows (대략적) 내보내기
/*!40000 ALTER TABLE `projects_info` DISABLE KEYS */;
INSERT INTO `projects_info` (`id`, `closed_checked`, `complaint`, `content`, `favorite_cnt`, `notice`, `url`, `projects_id`) VALUES
	(1, NULL, 0, '주사위 굴리기', 0, '주사위 굴리는거 한번 만들어봤는데..', 'http://주사위_굴리기', 1),
	(2, NULL, 0, '사다리타기', 0, '소개합니다', 'http://사다리', 2),
	(3, NULL, 0, '사다리타기', 0, '사다리 타기 해보세요!!', 'http://사다리', 3),
	(4, NULL, 0, '사다리타기', 0, '간단한 사다리타기입니다', 'http://사다리', 4),
	(5, NULL, 0, '단순 게시판', 0, '소개합니다', 'http://단순_게시판', 5),
	(6, NULL, 0, '단순 게시판', 0, '게시판 만들어봤는데..', 'http://단순_게시판', 6),
	(7, NULL, 0, 'todo list', 0, '소개합니다', 'https://투두리스트', 7),
	(8, NULL, 0, 'todo list', 0, 'todo list 입니다. 피드백 부탁드려요', 'https://투두리스트', 8),
	(9, NULL, 0, 'SHabit', 0, '건강한 자세 습관화를 위한 사이트', 'https://shabit.site', 9),
	(10, NULL, 0, '개발새발', 0, '소개합니다', 'https://rough-code.com', 10),
	(11, NULL, 0, '개발새발', 0, '개발새발인 프로젝트 우리 눈에는 이쁘다! 많이 이용해주세요', 'https://rough-code.com', 11),
	(12, NULL, 0, '<p>개발새발인 프로젝트 우리 눈에는 이쁘다!</p><p>와서 토이프로젝트 공유하고 피드백 받아보세요!</p>', 0, '와서 토이프로젝트 공유하고 피드백 받아보세요!', 'https://rough-code.com', 12),
	(13, NULL, 0, 'EHabit', 0, '눈 건강을 위한 사이트', 'https://ehabit.site', 13),
	(14, NULL, 0, '면접 준비 사이트', 0, '면접 준비하러 들어오세요', 'https://면접', 14),
	(15, NULL, 0, '면접 준비 사이트', 0, '면접 준비하러 들어오세요', 'https://면접', 15),
	(16, NULL, 0, '냉장고 털자', 0, '추천 사이트입니다', 'https://냉장고_털어', 16),
	(17, NULL, 0, '냉장고 털자', 0, '추천 사이트입니다', 'https://냉장고_털어', 17),
	(18, NULL, 0, '냉장고 털자', 0, '추천 사이트입니다', 'https://냉장고_털어', 18),
	(19, NULL, 0, '냉장고 털자', 0, '냉장고 음식 재료로 레시피를 추천해드려요', 'https://냉장고_털어', 19),
	(20, NULL, 0, '로그인', 0, '그냥 만들어본 사이트. 로그인만 확인해주세요ㅠ', 'http://login', 20),
	(21, NULL, 0, '투두', 0, '소개합니다', 'http://투두', 21),
	(22, NULL, 0, '투두', 0, 'todo list 입니다. 그냥 또 재미로 ㅎㅎ', 'http://투두', 22),
	(23, NULL, 0, '관광지 추천', 0, '추천 알고리즘', 'http://관광_고고', 23),
	(24, NULL, 0, '관광지 추천', 0, '관광지 추천', 'http://관광_고고', 24),
	(25, NULL, 0, '관광지 추천', 0, '관광지 추천', 'http://관광_고고', 25),
	(26, NULL, 0, '관광지 추천', 0, '관광지 추천', 'http://관광_고고', 26),
	(27, NULL, 0, '관광지 추천', 0, '관광지 추천', 'http://관광_고고', 27),
	(28, NULL, 0, '포폴', 0, '포트폴리오사이트인데 괜찮나요?', 'https://kosy318.github.io/', 28),
	(29, NULL, 0, '여행가자', 0, '여행지 추천', 'http://여행고고', 29),
	(30, NULL, 0, '랜덤 사이트', 0, '소개합니다', 'http://랜덤게임', 30),
	(31, NULL, 0, '랜덤 사이트', 0, '랜덤한 게임을 즐겨봅시다', 'http://랜덤게임', 31),
	(32, NULL, 0, 'Ttarawa', 0, '따릉이 보조 사이트', 'http://ttarawa.com', 32),
	(33, NULL, 0, '춘식이 최고', 0, '춘식이 사진 모음 사이트', 'http://춘식사진모음', 33),
	(34, NULL, 0, 'todo list', 0, '소개합니다', 'http://todo리스트', 34),
	(35, NULL, 0, 'todo list', 0, 'todo list 오랜만에 연습해봤어요. 피드백 부탁드려요', 'http://todo리스트', 35),
	(36, NULL, 0, '화장품 추천', 0, '화장품 추천해주는 사이트입니다. 피드백 받고싶어요', 'http://화장품추천', 36),
	(37, NULL, 0, '개발새발', 0, '소개합니다', 'http://rough-code.com', 37);
/*!40000 ALTER TABLE `projects_info` ENABLE KEYS */;

-- 테이블 roughcode.project_favorites 구조 내보내기
CREATE TABLE IF NOT EXISTS `project_favorites` (
  `favorites_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projects_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  `content` text DEFAULT NULL,
  PRIMARY KEY (`favorites_id`),
  KEY `FKs2bhak07xllfma3cvrjrq0e3o` (`projects_id`),
  KEY `FKfhghx2puof27mcsv07x2vytw2` (`users_id`),
  CONSTRAINT `FKfhghx2puof27mcsv07x2vytw2` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKs2bhak07xllfma3cvrjrq0e3o` FOREIGN KEY (`projects_id`) REFERENCES `projects` (`projects_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.project_favorites:~29 rows (대략적) 내보내기
/*!40000 ALTER TABLE `project_favorites` DISABLE KEYS */;
INSERT INTO `project_favorites` (`favorites_id`, `projects_id`, `users_id`, `content`) VALUES
	(1, 1, 1, NULL),
	(2, 4, 1, NULL),
	(3, 5, 1, NULL),
	(4, 2, 1, NULL),
	(5, 23, 1, NULL),
	(6, 11, 1, NULL),
	(7, 10, 1, NULL),
	(8, 12, 1, NULL),
	(9, 1, 2, NULL),
	(10, 14, 2, NULL),
	(11, 25, 2, NULL),
	(12, 20, 2, NULL),
	(13, 37, 2, NULL),
	(14, 27, 2, NULL),
	(15, 19, 2, NULL),
	(16, 12, 2, NULL);
/*!40000 ALTER TABLE `project_favorites` ENABLE KEYS */;

-- 테이블 roughcode.project_likes 구조 내보내기
CREATE TABLE IF NOT EXISTS `project_likes` (
  `likes_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `users_id` bigint(20) unsigned NOT NULL,
  `projects_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`likes_id`),
  KEY `FK6iognd66l6qs7f4068yul39cl` (`users_id`),
  KEY `FKr8urnhlhksa7hvvuf4vn3adg3` (`projects_id`),
  CONSTRAINT `FK6iognd66l6qs7f4068yul39cl` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKr8urnhlhksa7hvvuf4vn3adg3` FOREIGN KEY (`projects_id`) REFERENCES `projects` (`projects_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.project_likes:~28 rows (대략적) 내보내기
/*!40000 ALTER TABLE `project_likes` DISABLE KEYS */;
INSERT INTO `project_likes` (`likes_id`, `users_id`, `projects_id`) VALUES
	(1, 1, 1),
	(2, 1, 4),
	(3, 1, 5),
	(4, 1, 2),
	(5, 1, 23),
	(6, 1, 11),
	(7, 1, 10),
	(8, 1, 12),
	(9, 2, 1),
	(10, 2, 14),
	(11, 2, 25),
	(12, 2, 20),
	(13, 2, 37),
	(14, 2, 27),
	(15, 2, 19),
	(16, 2, 12),
	(17, 1, 20),
	(18, 1, 24),
	(19, 1, 25),
	(20, 1, 22);
/*!40000 ALTER TABLE `project_likes` ENABLE KEYS */;

-- 테이블 roughcode.project_selected_tags 구조 내보내기
CREATE TABLE IF NOT EXISTS `project_selected_tags` (
  `selected_tags_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projects_id` bigint(20) unsigned NOT NULL,
  `tags_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`selected_tags_id`),
  KEY `FK1ugwxlndmtu5mf3ne7rvbmxat` (`projects_id`),
  KEY `FKsqg29xoqswbs5vkgf2e4l96ak` (`tags_id`),
  CONSTRAINT `FK1ugwxlndmtu5mf3ne7rvbmxat` FOREIGN KEY (`projects_id`) REFERENCES `projects` (`projects_id`),
  CONSTRAINT `FKsqg29xoqswbs5vkgf2e4l96ak` FOREIGN KEY (`tags_id`) REFERENCES `project_tags` (`tags_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.project_selected_tags:~363 rows (대략적) 내보내기
/*!40000 ALTER TABLE `project_selected_tags` DISABLE KEYS */;
INSERT INTO `project_selected_tags` (`selected_tags_id`, `projects_id`, `tags_id`) VALUES
	(1, 10, 1),
	(2, 11, 1),
	(3, 12, 1),
	(4, 37, 1),
	(5, 10, 194),
	(6, 11, 194),
	(7, 12, 194),
	(8, 37, 194),
	(9, 10, 53),
	(10, 11, 53),
	(11, 12, 53),
	(12, 37, 53),
	(13, 10, 9),
	(14, 11, 9),
	(15, 12, 9),
	(16, 37, 9),
	(17, 10, 69),
	(18, 11, 69),
	(19, 12, 69),
	(20, 34, 69),
	(21, 10, 70),
	(22, 11, 70),
	(23, 12, 70),
	(24, 37, 70),
	(25, 10, 179),
	(26, 11, 179),
	(27, 12, 179),
	(28, 37, 179),
	(29, 10, 128),
	(30, 11, 128),
	(31, 12, 128),
	(32, 1, 1),
	(33, 3, 1),
	(34, 3, 194),
	(35, 4, 70),
	(36, 5, 179),
	(37, 5, 179),
	(38, 5, 6),
	(39, 6, 6),
	(40, 7, 6),
	(41, 15, 17),
	(42, 15, 1),
	(43, 14, 17),
	(44, 14, 1),
	(45, 22, 9),
	(46, 22, 1),
	(47, 22, 194),
	(48, 23, 70),
	(49, 23, 9),
	(50, 27, 9),
	(51, 29, 1),
	(52, 31, 1),
	(53, 33, 1),
	(54, 34, 1),
	(55, 35, 6),
	(56, 35, 7),
	(57, 35, 2);
/*!40000 ALTER TABLE `project_selected_tags` ENABLE KEYS */;

-- 테이블 roughcode.project_tags 구조 내보내기
CREATE TABLE IF NOT EXISTS `project_tags` (
  `tags_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cnt` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`tags_id`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.project_tags:~198 rows (대략적) 내보내기
/*!40000 ALTER TABLE `project_tags` DISABLE KEYS */;
INSERT INTO `project_tags` (`tags_id`, `cnt`, `name`) VALUES
	(1, 17, 'Java'),
	(2, 17, 'JavaScript'),
	(3, 10, 'Python'),
	(4, 8, 'Ruby'),
	(5, 0, 'PHP'),
	(6, 7, 'HTML'),
	(7, 7, 'CSS'),
	(8, 0, 'jQuery'),
	(9, 5, 'React'),
	(10, 6, 'Angular'),
	(11, 0, 'Vue.js'),
	(12, 7, 'Ember.js'),
	(13, 1, 'Backbone.js'),
	(14, 0, 'Bootstrap'),
	(15, 0, 'Materialize'),
	(16, 0, 'Foundation'),
	(17, 0, 'Bulma'),
	(18, 0, 'Semantic UI'),
	(19, 0, 'Sass'),
	(20, 0, 'Less'),
	(21, 0, 'Stylus'),
	(22, 7, 'Lodash'),
	(23, 0, 'Moment.js'),
	(24, 0, 'Underscore.js'),
	(25, 7, 'D3.js'),
	(26, 7, 'Chart.js'),
	(27, 0, 'Webpack'),
	(28, 0, 'Gulp'),
	(29, 0, 'Grunt'),
	(30, 0, 'Parcel'),
	(31, 0, 'WCAG'),
	(32, 1, 'ARIA'),
	(33, 0, 'Service Workers'),
	(34, 0, 'Web App Manifest'),
	(35, 7, 'Push Notifications'),
	(36, 7, 'Jest'),
	(37, 7, 'Enzyme'),
	(38, 0, 'Cypress'),
	(39, 7, 'Puppeteer'),
	(40, 0, 'TestCafe'),
	(41, 0, 'Redux'),
	(42, 0, 'MobX'),
	(43, 0, 'Vuex'),
	(44, 7, 'Material UI'),
	(45, 1, 'Ant Design'),
	(46, 0, 'Semantic UI React'),
	(47, 0, 'React Bootstrap'),
	(48, 0, 'GreenSock'),
	(49, 2, 'Anime.js'),
	(50, 0, 'React Spring'),
	(51, 0, 'Framer Motion'),
	(52, 0, 'Gatsby'),
	(53, 12, 'Next.js'),
	(54, 0, 'Nuxt.js'),
	(55, 7, 'Rust'),
	(56, 0, 'C/C++'),
	(57, 0, 'AssemblyScript'),
	(58, 0, 'Microservices Architecture'),
	(59, 0, 'Service Discovery'),
	(60, 0, 'Service Registry'),
	(61, 1, 'Circuit Breaker'),
	(62, 1, 'API Gateway'),
	(63, 0, 'REST'),
	(64, 0, 'GraphQL'),
	(65, 0, 'JSON-RPC'),
	(66, 0, 'XML-RPC'),
	(67, 22, 'Amazon Web Services (AWS)'),
	(68, 0, 'Elastic Beanstalk'),
	(69, 0, 'EC2'),
	(70, 0, 'S3'),
	(71, 0, 'RDS'),
	(72, 0, 'DynamoDB'),
	(73, 0, 'Lambda'),
	(74, 7, 'Kubernetes'),
	(75, 0, 'Google Cloud Platform (GCP)'),
	(76, 0, 'Microsoft Azure'),
	(77, 0, 'Serverless'),
	(78, 7, 'Function-as-a-Service (FaaS)'),
	(79, 7, 'Infrastructure-as-Code (IaC)'),
	(80, 0, 'Cloud-Native Storage'),
	(81, 0, 'Docker Swarm'),
	(82, 35, 'Amazon ECS'),
	(83, 0, 'Google Kubernetes Engine (GKE)'),
	(84, 1, 'Azure Kubernetes Service (AKS)'),
	(85, 0, 'Node.js'),
	(86, 7, 'Express.js'),
	(87, 0, 'Django'),
	(88, 0, 'Flask'),
	(89, 0, 'Ruby on Rails'),
	(90, 0, 'SQL'),
	(91, 0, 'NoSQL'),
	(92, 0, 'Oracle'),
	(93, 7, 'Microsoft SQL Server'),
	(94, 0, 'MariaDB'),
	(95, 0, 'Cassandra'),
	(96, 0, 'Redis'),
	(97, 0, 'Firebase'),
	(98, 0, 'JUnit'),
	(99, 0, 'Mockito'),
	(100, 0, 'Selenium'),
	(101, 7, 'Cucumber'),
	(102, 0, 'TestNG'),
	(103, 0, 'Chai'),
	(104, 0, 'Mocha'),
	(105, 0, 'OAuth'),
	(106, 0, 'JWT'),
	(107, 0, 'SSL'),
	(108, 0, 'TLS'),
	(109, 0, 'OWASP'),
	(110, 0, 'Penetration Testing'),
	(111, 0, 'Encryption'),
	(112, 7, 'Prometheus'),
	(113, 0, 'Grafana'),
	(114, 7, 'ELK Stack'),
	(115, 0, 'Splunk'),
	(116, 0, 'Nagios'),
	(117, 0, 'DevOps'),
	(118, 0, 'Continuous Integration'),
	(119, 0, 'Continuous Deployment'),
	(120, 2, 'Ansible'),
	(121, 0, 'Chef'),
	(122, 7, 'Puppet'),
	(123, 0, 'Terraform'),
	(124, 0, 'Consul'),
	(125, 0, 'Object-Oriented Programming (OOP)'),
	(126, 0, 'Design Patterns'),
	(127, 0, 'SOLID principles'),
	(128, 0, 'TDD'),
	(129, 0, 'BDD'),
	(130, 38, 'Agile'),
	(131, 0, 'Scrum'),
	(132, 1, 'Kanban'),
	(133, 7, 'Lean'),
	(134, 7, 'MVP'),
	(135, 0, 'Waterfall'),
	(136, 5, 'Android'),
	(137, 7, 'iOS'),
	(138, 0, 'Swift'),
	(139, 7, 'Kotlin'),
	(140, 3, 'React Native'),
	(141, 0, 'Flutter'),
	(142, 0, 'Hadoop'),
	(143, 0, 'Spark'),
	(144, 0, 'Kafka'),
	(145, 7, 'Hive'),
	(146, 0, 'Pig'),
	(147, 0, 'MapReduce'),
	(148, 0, 'Flink'),
	(149, 0, 'TensorFlow'),
	(150, 0, 'Keras'),
	(151, 0, 'PyTorch'),
	(152, 0, 'Scikit-Learn'),
	(153, 0, 'Natural Language Processing (NLP)'),
	(154, 0, 'Computer Vision'),
	(155, 0, 'Raspberry Pi'),
	(156, 0, 'Arduino'),
	(157, 0, 'MQTT'),
	(158, 0, 'Zigbee'),
	(159, 0, 'LoRaWAN'),
	(160, 7, 'Ethereum'),
	(161, 0, 'Solidity'),
	(162, 0, 'Hyperledger Fabric'),
	(163, 7, 'Corda'),
	(164, 0, 'VMWare'),
	(165, 0, 'VirtualBox'),
	(166, 0, 'Vagrant'),
	(167, 1, 'Linux'),
	(168, 0, 'Unix'),
	(169, 0, 'Windows'),
	(170, 0, 'MacOS'),
	(171, 0, 'TCP/IP'),
	(172, 0, 'DNS'),
	(173, 0, 'HTTP/HTTPS'),
	(174, 7, 'Load Balancing'),
	(175, 0, 'CDN'),
	(176, 0, 'Firewall'),
	(177, 0, 'VPN'),
	(178, 3, 'Apache'),
	(179, 0, 'Nginx'),
	(180, 0, 'Tomcat'),
	(181, 7, 'IIS'),
	(182, 0, 'SOAP'),
	(183, 0, 'RabbitMQ'),
	(184, 0, 'Event Sourcing'),
	(185, 7, 'CQRS'),
	(186, 0, 'Jira'),
	(187, 0, 'Trello'),
	(188, 11, 'Asana'),
	(189, 0, 'Basecamp'),
	(190, 7, 'Elastic Stack'),
	(191, 0, 'Logstash'),
	(192, 0, 'Kibana'),
	(193, 0, 'Elastic Search'),
	(194, 27, 'SpringBoot'),
	(195, 0, 'MySQL'),
	(196, 0, 'PostgreSQL'),
	(197, 0, 'MongoDB'),
	(198, 0, 'CouchDB');
/*!40000 ALTER TABLE `project_tags` ENABLE KEYS */;

-- 테이블 roughcode.rereviews 구조 내보내기
CREATE TABLE IF NOT EXISTS `rereviews` (
  `rereviews_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `complaint` text DEFAULT NULL,
  `content` text NOT NULL,
  `like_cnt` int(11) DEFAULT NULL,
  `reviews_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned DEFAULT NULL,
  `complained` bit(1) DEFAULT NULL,
  PRIMARY KEY (`rereviews_id`),
  KEY `FK5k6rto3bri589evgiua8vgp84` (`reviews_id`),
  KEY `FK6ew0k08uu7mlkvopec3fp7t3d` (`users_id`),
  CONSTRAINT `FK5k6rto3bri589evgiua8vgp84` FOREIGN KEY (`reviews_id`) REFERENCES `reviews` (`reviews_id`),
  CONSTRAINT `FK6ew0k08uu7mlkvopec3fp7t3d` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.rereviews:~27 rows (대략적) 내보내기
/*!40000 ALTER TABLE `rereviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `rereviews` ENABLE KEYS */;

-- 테이블 roughcode.rereview_complains 구조 내보내기
CREATE TABLE IF NOT EXISTS `rereview_complains` (
  `complains_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `rereviews_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`complains_id`),
  KEY `FK23rxhkfi97f1c065rcpr746vk` (`rereviews_id`),
  KEY `FK4mkqa3kkl6ix8y36ul4y1kjb2` (`users_id`),
  CONSTRAINT `FK23rxhkfi97f1c065rcpr746vk` FOREIGN KEY (`rereviews_id`) REFERENCES `rereviews` (`rereviews_id`),
  CONSTRAINT `FK4mkqa3kkl6ix8y36ul4y1kjb2` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.rereview_complains:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `rereview_complains` DISABLE KEYS */;
/*!40000 ALTER TABLE `rereview_complains` ENABLE KEYS */;

-- 테이블 roughcode.rereview_likes 구조 내보내기
CREATE TABLE IF NOT EXISTS `rereview_likes` (
  `likes_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `rereviews_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`likes_id`),
  KEY `FKe5br2p2wqqe8da8tljxcf53e9` (`rereviews_id`),
  KEY `FKfcg1716f2rnk6mh1yddxv32tv` (`users_id`),
  CONSTRAINT `FKe5br2p2wqqe8da8tljxcf53e9` FOREIGN KEY (`rereviews_id`) REFERENCES `rereviews` (`rereviews_id`),
  CONSTRAINT `FKfcg1716f2rnk6mh1yddxv32tv` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.rereview_likes:~9 rows (대략적) 내보내기
/*!40000 ALTER TABLE `rereview_likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `rereview_likes` ENABLE KEYS */;

-- 테이블 roughcode.reviews 구조 내보내기
CREATE TABLE IF NOT EXISTS `reviews` (
  `reviews_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `code_content` longtext DEFAULT NULL,
  `complaint` text DEFAULT NULL,
  `content` text DEFAULT NULL,
  `like_cnt` int(11) DEFAULT NULL,
  `line_numbers` text DEFAULT NULL,
  `selected` int(11) DEFAULT NULL,
  `codes_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned DEFAULT NULL,
  `complained` bit(1) DEFAULT NULL,
  PRIMARY KEY (`reviews_id`),
  KEY `FKc8vnvghqa29i4ai8f2ydc8dba` (`codes_id`),
  KEY `FK4v58b2u3xxgms89ps5omhjpy4` (`users_id`),
  CONSTRAINT `FK4v58b2u3xxgms89ps5omhjpy4` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKc8vnvghqa29i4ai8f2ydc8dba` FOREIGN KEY (`codes_id`) REFERENCES `codes` (`codes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.reviews:~25 rows (대략적) 내보내기
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;

-- 테이블 roughcode.review_complains 구조 내보내기
CREATE TABLE IF NOT EXISTS `review_complains` (
  `complains_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `reviews_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`complains_id`),
  KEY `FKhpkqaesyua0v60862vcj2rhhq` (`reviews_id`),
  KEY `FKf24i5b7oaom68586cdool9012` (`users_id`),
  CONSTRAINT `FKf24i5b7oaom68586cdool9012` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`),
  CONSTRAINT `FKhpkqaesyua0v60862vcj2rhhq` FOREIGN KEY (`reviews_id`) REFERENCES `reviews` (`reviews_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.review_complains:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `review_complains` DISABLE KEYS */;
/*!40000 ALTER TABLE `review_complains` ENABLE KEYS */;

-- 테이블 roughcode.review_likes 구조 내보내기
CREATE TABLE IF NOT EXISTS `review_likes` (
  `likes_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `reviews_id` bigint(20) unsigned NOT NULL,
  `users_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`likes_id`),
  KEY `FK5j4s8owj2melsxvane5hhkcbg` (`reviews_id`),
  KEY `FKrr07g0xu3g8b40vxaamb8hnpv` (`users_id`),
  CONSTRAINT `FK5j4s8owj2melsxvane5hhkcbg` FOREIGN KEY (`reviews_id`) REFERENCES `reviews` (`reviews_id`),
  CONSTRAINT `FKrr07g0xu3g8b40vxaamb8hnpv` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.review_likes:~7 rows (대략적) 내보내기
/*!40000 ALTER TABLE `review_likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `review_likes` ENABLE KEYS */;

-- 테이블 roughcode.selected_feedbacks 구조 내보내기
CREATE TABLE IF NOT EXISTS `selected_feedbacks` (
  `selected_feedbacks_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `feedbacks_id` bigint(20) unsigned NOT NULL,
  `selected_project_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`selected_feedbacks_id`),
  KEY `FKetb5r2doa80r09oae9y5n0ni2` (`feedbacks_id`),
  KEY `FKoub4d11bhnuvll0ep6xtdd15j` (`selected_project_id`),
  CONSTRAINT `FKetb5r2doa80r09oae9y5n0ni2` FOREIGN KEY (`feedbacks_id`) REFERENCES `feedbacks` (`feedbacks_id`),
  CONSTRAINT `FKoub4d11bhnuvll0ep6xtdd15j` FOREIGN KEY (`selected_project_id`) REFERENCES `projects` (`projects_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.selected_feedbacks:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `selected_feedbacks` DISABLE KEYS */;
INSERT INTO `selected_feedbacks` (`selected_feedbacks_id`, `feedbacks_id`, `selected_project_id`) VALUES
	(1, 2, 11),
	(2, 5, 12),
	(3, 6, 12);
/*!40000 ALTER TABLE `selected_feedbacks` ENABLE KEYS */;

-- 테이블 roughcode.selected_reviews 구조 내보내기
CREATE TABLE IF NOT EXISTS `selected_reviews` (
  `selected_reviews_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `selected_codes_id` bigint(20) unsigned NOT NULL,
  `reviews_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`selected_reviews_id`),
  KEY `FKai1qm56xla58wfg46nwkd8l5c` (`reviews_id`),
  KEY `FK2hqus20twytcv56txgoso9929` (`selected_codes_id`),
  CONSTRAINT `FK2hqus20twytcv56txgoso9929` FOREIGN KEY (`selected_codes_id`) REFERENCES `codes_info` (`id`),
  CONSTRAINT `FKai1qm56xla58wfg46nwkd8l5c` FOREIGN KEY (`reviews_id`) REFERENCES `reviews` (`reviews_id`),
  CONSTRAINT `FKaxeuwupgovco4liyc7kyhp4f9` FOREIGN KEY (`selected_codes_id`) REFERENCES `codes` (`codes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.selected_reviews:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `selected_reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `selected_reviews` ENABLE KEYS */;

-- 테이블 roughcode.users 구조 내보내기
CREATE TABLE IF NOT EXISTS `users` (
  `users_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `codes_cnt` bigint(20) unsigned DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(30) NOT NULL,
  `projects_cnt` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`users_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.users:~14 rows (대략적) 내보내기
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`users_id`, `created_date`, `modified_date`, `codes_cnt`, `email`, `name`, `projects_cnt`) VALUES
	(1, '2023-05-19 02:59:30.000000', '2023-05-19 02:59:30.000000', 0, '', 'hani2057', 0),
	(2, '2023-05-19 02:59:30.000000', '2023-05-19 02:59:30.000000', 0, '', 'kosy318', 0),
	(3, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', 0, '', 'charmdew', 0),
	(4, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', 0, '', 'eumyang99', 0),
	(5, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', 0, '', 'imaginewarmgun', 0),
	(6, '2023-05-19 02:59:31.000000', '2023-05-19 02:59:31.000000', 0, '', 'cmkds', 0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- 테이블 roughcode.users_roles 구조 내보내기
CREATE TABLE IF NOT EXISTS `users_roles` (
  `users_users_id` bigint(20) unsigned NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKp7sa9h0r8u3p0nuwkdf8iu2h7` (`users_users_id`),
  CONSTRAINT `FKp7sa9h0r8u3p0nuwkdf8iu2h7` FOREIGN KEY (`users_users_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 roughcode.users_roles:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`users_users_id`, `roles`) VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_USER'),
	(3, 'ROLE_USER');
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

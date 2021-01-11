## SQL Query Evaluator 


## Authors
* Ishan Gaikwad(ishangaikwad777@gmail.com)
* Jidnyasa Kolhe(jkolhe@umassd.edu)
* Kedar Sambhus(ksambhus@umassd.edu)

## Description
This project was for a course on Database Design (CIS 552).

We implemented an SQL query evaluator with operational support for Select, Project, Join, Union, Aggregate, and standard optimization techniques such as projection pushdown, selection pushdown and cross product to join conversion. Our program will receive a SQL File with a collection of statements. They include:
* CREATE TABLE: Defines a representational schema for the data.
* SELECT:   Performs filtering and other operations on the data.
The result of these operations will be in a standardized row-oriented data form.

### Parser

An SQL parser parses a query into an intelligible string field.  An open-source SQL parser was used in this project. Documentation and guidelines on how to use this parser is available at: https://doc.odin.cse.buffalo.edu/jsqlparser/

### Schema
The data directory contains files named table name.csv where table name is the name used in a CREATE TABLE statement. The result of CREATE TABLE statements is not to create a new file, but simply to synchronize the given TCPH sql schema to an existing .csv file. Delimiters used in these files, (a) vertical-pipe ("|") as a field delimiter, and (b) newlines ("\n") as record delimiters.

## Execution

Run this program using the following syntax:

	java -jar checkpoint1.jar TCPH-queries/queries.SQL [data_directory] 
	• data directory: A path to a directory containing TCPH data for this test. 
	• The generated data for this test is approximately 100mb in size.
	• For each CREATE TABLE table name statement, there is a corresponding table .csv in the data directory.
	• TCPH-queries : A path to a directory containing TCPH queries for this test
	• query.sql: One or more sql files for us to parse and evaluate.
	
	Example:
	$> ls TCPH-data-0.1
	CUSTOMER.csv
	LINEITEM.csv
	NATION.csv
	$> ls TCPH-queries
	1.sql
	2.sql
	3.sql
	$> cat CUSTOMER.csv
	1|Customer#000000001|IVhzIApeRb ot,c,E|15|25-989-741-2988|711.56|BUILDING|to the even, regular platelets. regular, ironic epitaphs nag e|
	2|Customer#000000002|XSTf4,NCwDVaWNe6tEgvwfmRchLXak|13|23-768-687-3665|121.65|AUTOMOBILE|l accounts. blithely ironic theodolites integrate boldly: caref|
	3|Customer#000000003|MG9kdTD2WBHm|1|11-719-748-3364|7498.12|AUTOMOBILE| deposits eat slyly ironic, even instructions. express foxes detect slyly. blithely even accounts abov|
	$> cat query.sql
	CREATE TABLE LINEITEM(
			orderkey INT,
			partkey  INT,
			suppkey  INT,
			linenumber  INT,
			quantity DECIMAL,
			extendedprice  DECIMAL,
			discount DECIMAL,
			tax  DECIMAL,
			returnflag  CHAR(1),
			linestatus  CHAR(1),
			shipdate DATE,
			commitdate DATE,
			receiptdate  DATE,
			shipinstruct  CHAR(25),
			shipmode CHAR(10),
			**comment**  VARCHAR(44)
		);
	SELECT returnflag, linestatus,
	SUM(quantity)  **AS**  sum_qty,
	SUM(extendedprice) AS sum_base_price, 
	SUM(extendedprice * (1-discount)) AS sum_disc_price,
	SUM(extendedprice * (1-discount)*(1+tax)) AS sum_charge,
	AVG(quantity) AS avg_qty, 
	AVG(extendedprice) AS avg_price, 
	AVG(discount) AS avg_disc, 
	COUNT(*) AS count_order 
	FROM LINEITEM 
	WHERE shipdate <= {d'1997-09-01'} 
	GROUP BY returnflag, linestatus;
	
	Program arguments:
	$> " " "TCPH-queries/1.SQL" "TCPH-data-0.1"
	Output: 
	= 
	'A'|'F'|3774200.0|5.32075388068998E9|5.054096266682835E9|5.256751331449267E9|25.537587116854997|36002.123829014|0.05014459706345448|147790 
	'N'|'F'|95257.0|1.3373779583999994E8|1.271323726512E8|1.3228629122944473E8|25.30066401062417|35521.32691633465|0.049394422310757295|3765 
	'N'|'O'|5151515.0|7.261654758889814E9|6.898158833526641E9|7.17391659450853E9|25.56265971963776|36033.518218036545|0.05013244014396935|201525
	'R'|'F'|3785523.0|5.337950526469872E9|5.0718185329421E9|5.274405503049367E9|25.5259438574251|35994.029214030066|0.04998927856189752|148301 
	=


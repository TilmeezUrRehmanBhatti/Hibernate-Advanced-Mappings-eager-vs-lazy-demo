# Eager vs Lazy Loading

**Fetch Types: Eager vs Lazy Loading**
+ When we fetch / retrieve data, should we retrieve EVERYTHING?
    + **Eager** will retrieve everything
    + **Lazy** will retrieve on request

**Eager Loading**
+ Eager loading will load all dependent entities
    + Load instructor and all of their courses at once

<img src="https://user-images.githubusercontent.com/80107049/188460018-a2a4cc52-1c21-40ce-a52d-ac85ac508867.png" width=600 />

+ What about course and students?
    + Could easily turn into a performance nightmare ...

<img src="https://user-images.githubusercontent.com/80107049/188460093-ac6f1433-5a0b-47ff-8d20-7a6047889672.png" width = 300 />


+ In our app, if we are searching for a course by keyword
    + Only want a list of matching course

+ Eager loading would still **load all student** for **each course** ... not good!

> **Beat practice**
> > Only load data when absolutely needed
> >
>> Prefer lazy loading instead of Eager loading


**Lazy Loading**
+ Lazy loading will load the main entity first
    + Load dependent entities on demand(lazy)



<img src="https://user-images.githubusercontent.com/80107049/188460182-989e1315-62f3-46ec-9d7f-568c6bf9b90e.png" width = 300 />


+ When lazy load, the data is only retrieved on demand
+ However, this requires an open Hibernate session
    + need an connection to database to retrieve data

+ If Hibernate session is closed
    + And you attempt to retrieve lazy data
    + Hibernate will throw an exception

+ To retrieve lazy data, you will need to open a Hibernate session

+ Retrieve lazy data using
    + Option 1: session.get and call appropriate getter method(s)
    + Option 2: Hibernate query with HQL

+ Many other techniques available but two above are most common

**Real-World Use Case**

+ In Master view, use lazy loading
    + use lazy for search results
    + Only load instructor ... not their courses
+ In Detail view, retrieve the entity and necessary dependent entities
    + Load instructor AND their courses


**Fetch Type**
+ When defining the mapping relationship
    + Can specify the fetch type: EAGER or LAZY

```JAVA
@Entity
@Table(name="instructor")
public class Instructor {
 ...
  @OneToMany(fetch=FetchType.LAZY, mappedBy="instructor")
  private List<Course> courses;
  
 ... 
}
```

**Default Fetch Types**

| Mapping         | Default Fetch Type |
| --------------- | ------------------ |
| **@OneToOne**   | FetchType.EAGER    |
| **@OneToMany**  | FetchType.LAZY     |
| **@ManyToOne**  | FetchType.EAGER    |
| **@ManyToMany** | FetchType.LAZY     |


**Override Default Fetch Type**
+ Specifying the fetch type, overrides the defaults

```JAVA
@ManyToOne(ferch=FetchType.LAZY)
@JoinColumn(name="instructor_id")
private Instructor instructor;
```



Checks that catch parameter names conform to the following characteristic:

* allows names beginning with two lowercase letters followed by at least one uppercase or lowercase letter
* allows e abbreviation (suitable for exceptions end errors)
* allows ex abbreviation (suitable for exceptions)
* allows t abbreviation (suitable for throwables)
* prohibits numbered abbreviations like e1 or t2
* prohibits one letter prefixes like pException
* prohibits two letter abbreviations like ie or ee
* prohibits any other characters than letters

Valid:
````
catch(Exception txD) {}
catch(Exception txf) {}
catch(Exception e) {}
catch(Error e) {}
catch(Exception ex) {}
catch(Throwable t) {}
````

Invalid:
````
catch(Exception e2) {}
catch(Exception pExceptions) {}
catch(Exception gh) {}
catch(Exception e_x) {}
````

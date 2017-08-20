package hello

import(
	"fmt"
	"net/http"
)

type Person struct{
	name string
	sex  int
	married bool
	birthday string
}

var persons []Person;

func init(){
	persons = make([]Person,0);
	http.HandleFunc("/",showPerson)
	//http.HandleFunc("/Person/new",addPerson)
	http.ListenAndServe(":8080",nil)
}

func showPerson(w http.ResponseWriter,r *http.Request){
	fmt.Fprintln(w,"{\"name\":\"zhou guang bo\",")
	fmt.Fprintln(w,"\"sex\":\"1\"")
	fmt.Fprintln(w,"}")
}
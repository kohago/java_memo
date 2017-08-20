package main

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

func main(){
	persons = make([]Person,0);
	http.HandleFunc("/Person",showPerson)
	//http.HandleFunc("/Person/new",addPerson)
	http.ListenAndServe(":80",nil)
}

func showPerson(w http.ResponseWriter,r *http.Request){
	fmt.Fprintln(w,"{\"name\":\"zhou guang bo\"")
	fmt.Fprintln(w,"\"sex\":\"1\",")
	fmt.Fprintln(w,"}")
}
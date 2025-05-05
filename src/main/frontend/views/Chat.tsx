import {Button, TextField} from "@vaadin/react-components";
import {useState} from "react";
import {ChatService} from "Frontend/generated/endpoints";
import Markdown from "react-markdown";

export default function Chat(){
    // des etat d'objet react pour stocker la reponse et la question passer par l'utlisateur via le chatbot
    const [question,setQuestion] = useState<string>("");
    const [response,setRespoonse] = useState<string>("");

    async function send(){
        ChatService.RagChat(question).then(resp=>{setRespoonse(resp);})
    };
    return(
        <div className="p-m">
            <h3>Chat Bot</h3>
            <div>
                <TextField style={{width:'80%'}}
                onChange={(e=>setQuestion(e.target.value))}></TextField>
                {/*data binding*/}
                <Button theme="primary" onClick={send}>Send</Button>
                {/*styler le button en utilisant hala*/}
            </div>
            <div>
                <Markdown>
                    {response}
                </Markdown>
            </div>
        </div>
    );
}

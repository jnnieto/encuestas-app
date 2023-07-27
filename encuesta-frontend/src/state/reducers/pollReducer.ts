import { Poll, Question } from "../../types";
import { v4 as uuid }  from "uuid";
import produce from "immer";
import { PollActions } from "../actions/pollActions";

const defaultQuestion: Question = {
    id: uuid(),
    content: "",
    questionOrder: 1,
    type: "RADIO",
    answers: [
        {
            id: uuid(),
            content: ""
        }
    ]
}

const defaultPoll: Poll = {
    id: uuid(),
    content: "",
    errors: {},
    opened: true,
    questions: [defaultQuestion]
}

export const pollInitialState: Poll = {
    ...defaultPoll
}

export const PollReducer = produce((state: Poll, action: PollActions): Poll => {
    switch(action.type) {
        case "pollcontent": {
            state.content = action.content;
            return state;
        }
        default:
            return state;
    }
})
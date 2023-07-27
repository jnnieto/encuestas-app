import { useContext } from "react"
import { PollDispatchContext, PollStateContext } from "../context/pollContext"


export const usePollState = () => {
    const context = useContext(PollStateContext);
   
    if (context === undefined) {
        throw new Error("usePollState must be used within a PollProvider")
    }

    return context;
}

export const usePollDispatch = () => {
    const context = useContext(PollDispatchContext);
    
    if (context === undefined) {
        throw new Error("usePollDispatch must be used within a PollProvider")
    }
    
    return context;
}
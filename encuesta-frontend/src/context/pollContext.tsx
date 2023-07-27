import { createContext, Dispatch, FC, ReactNode, useReducer } from "react";
import { PollActions } from "../state/actions/pollActions";
import { pollInitialState, PollReducer } from "../state/reducers/pollReducer";
import { Poll } from "../types";

const PollStateContext = createContext<Poll>(pollInitialState);

const PollDispatchContext = createContext<Dispatch<PollActions>>(() => undefined);

interface PollProviderProps {
    children: ReactNode
}

const PollProvider:FC<PollProviderProps> = ({ children }) => {
  const [poll, dispatch] = useReducer(PollReducer, pollInitialState);
  return (
      <PollStateContext.Provider value={poll}>
        <PollDispatchContext.Provider value={dispatch}>
            {children}
        </PollDispatchContext.Provider>
      </PollStateContext.Provider>
  )
}

export {
    PollStateContext,
    PollDispatchContext
}

export default PollProvider;
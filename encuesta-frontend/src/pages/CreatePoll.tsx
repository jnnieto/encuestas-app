import Poll from "../components/poll/Poll";
import PollProvider from "../context/pollContext";

const CreatePoll = () => {
  return (
    <PollProvider>
      <Poll></Poll>
    </PollProvider>
  );
};

export default CreatePoll;

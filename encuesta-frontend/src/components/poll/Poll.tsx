import { usePollDispatch, usePollState } from "../../hooks/usePollState";
import Question from "./Question";

const Poll = () => {
  const poll = usePollState();
  const pollDispatch = usePollDispatch();

  const renderQuestion = () => {
    return poll.questions?.map((question, index) => {
      return <Question key={question.id} index={index}></Question>
    })
  }

  return (
    <div className="p-10 flex justify-center mx-auto md:w-3/4 sm:w-4/5 mt-5 rounded-md shadow-md">
      <div className="w-full md:w-100 px-3 mb-6 md:mb-0">
        <label
          className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
          htmlFor="title"
        >
          Título
        </label>
        <input
          className="appearance-none block w-full bg-gray-200 text-gray-700 border rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
          id="title"
          type="text"
          placeholder="Título de la encuesta"
          value={poll.content}
          onChange={e => pollDispatch({
            type: "pollcontent",
            content: e.target.value
          })}
        />
        {/* <p className="text-red-500 text-xs italic">Please fill out this field.</p> */}
      { renderQuestion() }
      </div>
    </div>
  );
};

export default Poll;

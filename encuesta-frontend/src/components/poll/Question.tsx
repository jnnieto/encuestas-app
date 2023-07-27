import { FC } from "react"

interface QuestionProps {
    index: number
}

const Question: FC<QuestionProps> = ({ index }) => {
  return (
    <div>Question</div>
  )
}

export default Question
export type User = {
    email: string,
    token: string,
    isAuthenticated: boolean
}

export type Poll = {
    id: string,
    errors: {},
    content: string,
    opened: boolean,
    questions: Question[]
}

export type Question = {
    id: string,
    content: string,
    questionOrder: number,
    type: QuestionType,
    answers: Answer[]
}

export type QuestionType = "RADIO" | "CHECKBOX" | "SELECT"

export type Answer = {
    id: string,
    content: string
}

export type RouteType = "PRIVATE" | "PUBLIC" | "GUEST";

export type Route = {
    path: string,
    element: any,
    routeType: RouteType
}
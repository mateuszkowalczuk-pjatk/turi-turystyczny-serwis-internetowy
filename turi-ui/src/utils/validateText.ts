export const validateText = (text: string | null) => {
    return text && text.trim().length > 0 ? text.trim() : null
}

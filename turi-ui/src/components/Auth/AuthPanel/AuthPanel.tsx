import React, { ReactNode } from 'react'
import styles from './AuthPanel.module.css'

interface Props {
    onSubmit: (e: React.FormEvent) => Promise<void>
    header: ReactNode
    option: ReactNode
    input: ReactNode
    error: ReactNode
    button: ReactNode
    top: ReactNode
    down: ReactNode
}

const AuthPanel = ({ onSubmit, header, option, input, error, button, top, down }: Props) => {
    return (
        <form
            className={styles.panel}
            onSubmit={onSubmit}
        >
            {header}
            {option}
            {input}
            {error}
            {button}
            {top}
            {down}
        </form>
    )
}

export default AuthPanel

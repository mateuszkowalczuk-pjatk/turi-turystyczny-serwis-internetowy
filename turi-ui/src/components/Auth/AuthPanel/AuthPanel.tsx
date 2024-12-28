import React, { ReactNode } from 'react'
import styles from './AuthPanel.module.css'

interface Props {
    onSubmit: (e: React.FormEvent) => Promise<void>
    header: ReactNode
    inputOrText: ReactNode
    firstInput: ReactNode
    secondInput?: ReactNode
    thirdInput?: ReactNode
    error: ReactNode
    button: ReactNode
    top: ReactNode
    down: ReactNode
    isRegister?: boolean
}

const AuthPanel = ({
    onSubmit,
    header,
    inputOrText,
    firstInput,
    secondInput,
    thirdInput,
    error,
    button,
    top,
    down,
    isRegister
}: Props) => {
    return (
        <form
            className={`${styles.panel} ${isRegister ? styles.register : ''}`}
            onSubmit={onSubmit}
        >
            {header}
            {inputOrText}
            {firstInput}
            {secondInput}
            {thirdInput}
            {error}
            {button}
            {top}
            {down}
        </form>
    )
}

export default AuthPanel

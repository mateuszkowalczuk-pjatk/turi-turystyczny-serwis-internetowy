import { ReactNode } from 'react'
import styles from './PersonalPart.module.css'

interface Props {
    firstPanel: ReactNode
    secondPanel: ReactNode
    option?: ReactNode
    button?: ReactNode
    isPremium?: boolean
}

const PersonalPart = ({ firstPanel, secondPanel, option, button, isPremium }: Props) => {
    return (
        <div className={`${styles.part} ${isPremium ? styles.partPremium : ''}`}>
            {firstPanel}
            {secondPanel}
            {option}
            {button}
        </div>
    )
}

export default PersonalPart

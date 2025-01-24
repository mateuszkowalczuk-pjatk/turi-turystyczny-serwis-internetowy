import { ReactNode } from 'react'
import styles from './InfoPanel.module.css'

interface Props {
    firstTitle: ReactNode
    firstText: ReactNode
    secondTitle: ReactNode
    secondText: ReactNode
    thirdTitle: ReactNode
    thirdText: ReactNode
    fourthTitle: ReactNode
    fourthText: ReactNode
}

const InfoPanel = ({
    firstTitle,
    firstText,
    secondTitle,
    secondText,
    thirdTitle,
    thirdText,
    fourthTitle,
    fourthText
}: Props) => {
    return (
        <div className={styles.panel}>
            {firstTitle}
            {firstText}
            {secondTitle}
            {secondText}
            {thirdTitle}
            {thirdText}
            {fourthTitle}
            {fourthText}
        </div>
    )
}

export default InfoPanel

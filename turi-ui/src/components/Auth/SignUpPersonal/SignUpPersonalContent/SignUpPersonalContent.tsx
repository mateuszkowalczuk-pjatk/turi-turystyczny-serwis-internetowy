import { useTranslation } from 'react-i18next'
import SignUpPersonalPart from '../SignUpPersonalPart'
import SignUpPersonalPanel from '../SignUpPersonalPanel'
import SignUpPersonalLabel from '../SignUpPersonalLabel'
import SignUpPersonalInput from '../SignUpPersonalInput'
import SignUpPersonalButtons from '../SignUpPersonalButtons'
import styles from './SignUpPersonalContent.module.css'

const SignUpPersonalContent = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.content}>
            <SignUpPersonalPart
                firstPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                    />
                }
                secondPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        thirdInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                    />
                }
                option={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                    />
                }
            />
            <SignUpPersonalPart
                firstPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                    />
                }
                secondPanel={
                    <SignUpPersonalPanel
                        label={
                            <SignUpPersonalLabel
                                text={t('')}
                            />
                        }
                        firstInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        secondInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        thirdInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        fourthInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        fifthInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                        sixthInput={
                            <SignUpPersonalInput
                                text={t('')}
                            />
                        }
                    />
                }
                option={
                    <SignUpPersonalButtons />
                }
            />
        </div>
    )
}

export default SignUpPersonalContent;